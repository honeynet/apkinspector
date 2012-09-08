package package2;

import package1.ProtectedCtor;

class BadProtectedCtorAccess {
    // this is not allowed
    Object o = new ProtectedCtor();
}
