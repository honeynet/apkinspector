#ifndef _JAVA_LANG_OBJECT
#define _JAVA_LANG_OBJECT

namespace java {
namespace lang {

String *makestring(const char *s);

class Object : public gc
{
public:
	Object() { };

	virtual bool equals(Object *obj) { return obj == this; };
	virtual String *toString() { return makestring("Object"); };
	
};

//namespaces
}
}

#endif



