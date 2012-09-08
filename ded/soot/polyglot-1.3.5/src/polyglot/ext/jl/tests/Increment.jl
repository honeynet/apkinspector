class Increment {
  int i;
  int m() {
    // This is debatable.  The JLS doesn't say if (i) is a variable,
    // but javac accepts the next 4 lines.  Polyglot does too, but
    // the parser rejects (i) = i + 1.
    (i)++;
    (i)--;
    ++(i);
    --(i);

    return 0;
  }
}
