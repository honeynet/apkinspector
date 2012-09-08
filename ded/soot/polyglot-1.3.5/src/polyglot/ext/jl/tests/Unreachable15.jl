class Unreachable15 {
  int m(boolean r) {
    try {
      if (r) return 0;
      return 1;
    }
    finally { }
    return 1;
  }
}
