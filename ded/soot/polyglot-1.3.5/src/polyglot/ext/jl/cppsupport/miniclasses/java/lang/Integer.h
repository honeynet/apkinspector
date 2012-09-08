#ifndef _JAVA_LANG_INTEGER
#define _JAVA_LANG_INTEGER

namespace java {
namespace lang {

class Integer : public Number, Comparable
{
public:
	Integer(int i);
	Integer(String *s);

	virtual int intValue() { return val; };
	virtual int compareTo(Object *o);
	
	virtual String *toString();

	static int parseInt(String *s);
	static String *toString(int i);
	static Integer *valueOf(int i) 
		{ return new Integer(i); };
	static Integer *valueOf(String *s) 
		{ return new Integer(parseInt(s)); };

private:
	int val;
};

//namespaces
}
}

#endif



