// This tests whether the implement-as-less-public error is caught in
// interface overrides.

interface Override {
  void method();
}

class Sub implements Override  {
  void method() {} 
}
