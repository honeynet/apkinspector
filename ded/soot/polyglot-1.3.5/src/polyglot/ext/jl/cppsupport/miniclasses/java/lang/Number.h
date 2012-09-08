#ifndef _JAVA_LANG_NUMBER
#define _JAVA_LANG_NUMBER

namespace java {
namespace lang {

class Number : public Object
{
public:
	Number() { };

	virtual int intValue() = 0;
	
	virtual String *toString() { return new String("Number"); };
};

//namespaces
}
}

#endif



