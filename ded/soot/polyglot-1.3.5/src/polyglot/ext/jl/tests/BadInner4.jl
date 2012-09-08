class Super {
}
class Outer extends Super {
  	class Inner{}
}
class ChildOfInner extends Outer.Inner {
  	ChildOfInner(){(new Super()).super();}
}

