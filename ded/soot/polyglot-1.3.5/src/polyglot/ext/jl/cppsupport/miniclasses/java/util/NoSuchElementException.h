#ifndef _JAVA_UTIL_NOSUCHELEMENT
#define _JAVA_UTIL_NOSUCHELEMENT

namespace java {
namespace util {

using namespace java::lang;

class NoSuchElementException : public Exception
{
public:
	NoSuchElementException():
	  Exception(new String("NoSuchElementException")) { };
	NoSuchElementException(int i):
	  Exception(Integer::toString(i)) { };
	NoSuchElementException(String *m):Exception(m) { };
        NoSuchElementException(Throwable *c):Exception(c) { };
        NoSuchElementException(String *m, Throwable *c):Exception(m,c) { };

};

//namespaces
}
}

#endif



