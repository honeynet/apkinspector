package package2;

import package1.ProtectedCtor;

class ProtectedCtorAccess {
    // this is allowed -- it's a subclass.
    Object o = new ProtectedCtor() {};
}
