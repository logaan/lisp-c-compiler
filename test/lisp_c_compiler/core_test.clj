(ns lisp-c-compiler.core-test
  (:use midje.sweet
        lisp-c-compiler.core))

(fact (parse-expression "(+ 1 1)")
      => '[[+ 1 1]] )

(fact (parse-expression "1(+ 1 (+ 1 1))")
      => '[1[+ 1 [+ 1 1]]] )

