class Return5 {
  int m(boolean r) {
    try {
      if (r) return 0;
    }
    finally { }
    return 1;
  }
}
