# lisp-c-compiler

A toy compiler I whipped up in an afternoon to see if I could.

## Usage

```clojure
(compile-string "(p (+ 1 1 (+ 2 2)))")
```

generates

```c
#include<stdio.h>

void printResult(int result) {
  printf("%i\n", result);
}

int main() {
  printResult((1+1+(2+2)));
  return 0;
}
```

## License

Copyright © 2013 Logan Campbell

Distributed under the Eclipse Public License, the same as Clojure.
