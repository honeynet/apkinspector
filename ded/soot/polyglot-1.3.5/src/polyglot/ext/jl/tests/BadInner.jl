class Outer {
      class Inner { class Inner2 { } }
}
 
class BadInner {
      public static void main(String[] args) {
	  // should be an error since Inner isn't static
	  Object o = new Outer.Inner();
	  Object p = new Outer();
      }
}
