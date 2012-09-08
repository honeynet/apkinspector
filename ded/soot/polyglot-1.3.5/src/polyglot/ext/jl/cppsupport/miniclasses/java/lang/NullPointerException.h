#ifndef _JAVA_LANG_NULLPTREXCEPTION
#define _JAVA_LANG_NULLPTREXCEPTION

namespace java {
namespace lang {

class NullPointerException : public RuntimeException
{
public:
	NullPointerException():RuntimeException(new String("NullPointerException")) { };
	NullPointerException(String *m):RuntimeException(m) { };
        NullPointerException(Throwable *c):RuntimeException(c) { };
        NullPointerException(String *m, Throwable *c):RuntimeException(m,c) { };

};

//namespaces
}
}

#endif



