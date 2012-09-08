#ifndef _JAVA_LANG_RUNTIMEEXCEPTION
#define _JAVA_LANG_RUNTIMEEXCEPTION

namespace java {
namespace lang {

class RuntimeException : public Exception
{
public:
	RuntimeException():Exception(new String("RuntimeException")) { };
	RuntimeException(String *m):Exception(m) { };
        RuntimeException(Throwable *c):Exception(c) { };
        RuntimeException(String *m, Throwable *c):Exception(m,c) { };

};

//namespaces
}
}

#endif



