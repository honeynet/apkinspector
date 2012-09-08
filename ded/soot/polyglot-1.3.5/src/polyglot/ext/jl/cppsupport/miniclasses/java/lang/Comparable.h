#ifndef _JAVA_LANG_COMPARABLE
#define _JAVA_LANG_COMPARABLE

namespace java {
namespace lang {

class Comparable
{
public:
	Comparable() { };

	virtual int compareTo(Object *o) = 0;
};

//namespaces
}
}

#endif



