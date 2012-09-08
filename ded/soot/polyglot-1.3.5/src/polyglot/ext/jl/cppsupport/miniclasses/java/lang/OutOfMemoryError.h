#ifndef _JAVA_LANG_OUTOFMEMORYERROR
#define _JAVA_LANG_OUTOFMEMORYERROR

namespace java {
namespace lang {

class OutOfMemoryError : public Exception
{
public:
	OutOfMemoryError():Exception(new String("OutOfMemoryError")) { };
	OutOfMemoryError(String *m):Exception(m) { };
        OutOfMemoryError(Throwable *c):Exception(c) { };
        OutOfMemoryError(String *m, Throwable *c):Exception(m,c) { };

};

//namespaces
}
}

#endif



