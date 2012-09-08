class Unreachable16 {
  int m(boolean r) {
    try {
      return 0;
    }
    finally { }
    return 1;
  }
}
