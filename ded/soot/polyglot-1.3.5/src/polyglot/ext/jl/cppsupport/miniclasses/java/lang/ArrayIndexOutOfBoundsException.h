#ifndef _JAVA_LANG_ARRAYINDEXOUTOFBOUNDS
#define _JAVA_LANG_ARRAYINDEXOUTOFBOUNDS

namespace java {
namespace lang {

class ArrayIndexOutOfBoundsException : public Exception
{
public:
	ArrayIndexOutOfBoundsException():
	  Exception(new String("ArrayIndexOutOfBoundsException")) { };
	ArrayIndexOutOfBoundsException(int i):
	  Exception(Integer::toString(i)) { };
	ArrayIndexOutOfBoundsException(String *m):Exception(m) { };
        ArrayIndexOutOfBoundsException(Throwable *c):Exception(c) { };
        ArrayIndexOutOfBoundsException(String *m, Throwable *c):Exception(m,c) { };

};

//namespaces
}
}

#endif



