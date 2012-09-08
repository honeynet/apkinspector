#ifndef _JAVA_UTIL_ITERATOR
#define _JAVA_UTIL_ITERATOR

namespace java {
namespace util {

class Iterator : public Object
{
public:
	Iterator() { };

	virtual bool hasNext() = 0;
	virtual Object *next() = 0;
	virtual void remove() { };
};

//namespaces
}
}

#endif



