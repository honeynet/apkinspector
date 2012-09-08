#ifndef _JAVA_LANG_EXCEPTION
#define _JAVA_LANG_EXCEPTION

namespace java {
namespace lang {

class Exception : public Throwable
{
public:
	Exception():Throwable(new String("Exception")) { };
	Exception(String *m):Throwable(m) { };
	Exception(Throwable *c):Throwable(c) { };
	Exception(String *m, Throwable *c):Throwable(m,c) { };

};

//namespaces
}
}

#endif



