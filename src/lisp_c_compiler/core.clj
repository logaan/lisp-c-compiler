(ns lisp-c-compiler.core
  (:require [clojure.string :as str]))

(defprotocol Match
  (match [self value]))

(extend-protocol Match
  java.util.regex.Pattern
  (match [self value]
         (re-matches self (str value)))

  java.lang.Character
  (match [self value]
         (= self value)))

(defn parse-char [c]
  (condp match c
    \space []
    \+     ['+]
    \p     ['print]
    #"\d"  [(Integer/parseInt (str c))]))

(defn parse-expression
  ([expression-string]
   (parse-expression [] expression-string))
  ([accumulator [head & tail]]
   (case head
     \( (conj accumulator (parse-expression [] tail))
     \) accumulator
     (recur (vec (concat accumulator (parse-char head))) tail))))

(defn c-boilerplate [contents]
  (str "#include<stdio.h>\n"
       "void printResult(int result) { printf(\"%i\\n\", result); }"
       "int main() {"
       (str/join ";" contents)
       "; return 0; }"))

(defn function-call? [form]
  (and (coll? form) (symbol? (first form))))

(defn list-of-expressions? [form]
  (and (coll? form) (not (function-call? form))))

(def builtins
  {'print (fn [contents]
            (str "printResult(" (str/join contents) ")"))
   '+     (fn [contents]
            (str "(" (str/join "+" contents) ")"))})

(defn compile-ast [ast]
  (cond
    (list-of-expressions? ast) (map compile-ast ast)
    (function-call? ast) ((builtins (first ast)) (compile-ast (rest ast)))
    (number? ast) (str ast)))

(defn compile-string [string]
  (c-boilerplate (compile-ast (parse-expression string))))

