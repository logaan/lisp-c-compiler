(ns lisp-c-compiler.core-test
  (:use midje.sweet
        lisp-c-compiler.core))

(fact (parse-expression "(+ 1 1)")
      => '[[+ 1 1]] )

(fact (parse-expression "1(+ 1 (+ 1 1))")
      => '[1 [+ 1 [+ 1 1]]] )

(fact (parse-expression "(p 1)")
      => '[[print 1]] )

(fact (method-call? '[+ 1 1])
      => true)

(fact (list-of-expressions? '[[+ 1 1] 1])
      => true)

(fact (compile-ast [])
      => [])

(fact ((builtins 'print) ["(1 + 1)"])
      => "printResult((1 + 1))")

(fact (compile-ast '[print 1])
      => "printResult(1)")

(fact (compile-ast '[print [+ 1 1]])
      => "printResult((1+1))")

(compile-string "(p (+ 1 1 (+ 2 2)))")
