class Outer {
  	class Inner{}
}
class ChildOfInner extends Outer.Inner {
  	ChildOfInner(){(new Outer()).super();}
}

