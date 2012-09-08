// Regression test for bug with checking class conformance.
// TypeSystem_c.checkClassConformance was checking that
// ConformanceCheck9 implements all inherited abstract
// methods.  For clone(), it was finding Object.clone(), which
// throws more exceptions than I.clone().  The check should not
// be done at all for abstract classes.
interface I {
  public Object clone();
}

abstract class ConformanceCheck9 implements I {
  public abstract Object clone();
}
