#ifndef _JAVA_UTIL_COLLECTION
#define _JAVA_UTIL_COLLECTION

namespace java {
namespace util {

class Collection : public Object
{
public:
	Collection() { };

	virtual bool add(Object *o) = 0;
	virtual void clear() = 0;
	virtual bool equals(Object *o) = 0;
	virtual bool isEmpty() = 0;
	virtual Iterator *iterator() = 0;
	virtual int size() = 0;
	virtual jmatch_array<Object*> *toArray() = 0;
};

//namespaces
}
}

#endif



