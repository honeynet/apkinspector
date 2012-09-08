class BadForwardRef {
  static final int x = y; // bad forward ref
  static final int y = 3; 
}
