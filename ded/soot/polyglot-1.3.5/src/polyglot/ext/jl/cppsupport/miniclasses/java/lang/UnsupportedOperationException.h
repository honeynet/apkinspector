#ifndef _JAVA_LANG_UNSUPPORTEDOPERATIONEXCEP
#define _JAVA_LANG_UNSUPPORTEDOPERATIONEXCEP

namespace java {
namespace lang {

class UnsupportedOperationException : public RuntimeException
{
public:
	UnsupportedOperationException():RuntimeException(new String("UnsupportedOperationException")) { };
	UnsupportedOperationException(String *m):RuntimeException(m) { };
        UnsupportedOperationException(Throwable *c):RuntimeException(c) { };
        UnsupportedOperationException(String *m, Throwable *c):RuntimeException(m,c) { };

};

//namespaces
}
}

#endif



