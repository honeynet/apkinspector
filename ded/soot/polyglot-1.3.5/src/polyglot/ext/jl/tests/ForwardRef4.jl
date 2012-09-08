class ForwardRef4 {
    int i = j; // compile-time error: incorrect forward reference
    int j = 1;
}
