(ns lisp-c-compiler.core)

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
    #"\d"  [(Integer/parseInt (str c))]))

(defn parse-expression
  ([expression-string]
   (parse-expression [] expression-string))
  ([accumulator [head & tail]]
   (case head
     \( (conj accumulator (parse-expression [] tail))
     \) accumulator
     (recur (vec (concat accumulator (parse-char head))) tail))))

