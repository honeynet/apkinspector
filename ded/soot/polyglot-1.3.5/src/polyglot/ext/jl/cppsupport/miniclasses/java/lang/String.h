#ifndef _JAVA_LANG_STRING
#define _JAVA_LANG_STRING

namespace java {
namespace lang {

class String : public Object, Comparable
{
public:
	String();
	String(const char *);
	String(const String &s);
	String(const String *s);
	String(const String *lh, const String *rh);

	virtual int length() const { return len; };
	virtual bool equals(Object *o);
	virtual int compareTo(Object *o);
	virtual char charAt(int index) { return data[index]; };

	virtual String *toString() { return this; };

	virtual const char *getBytes() const { return data; };
private:
	char *data;	
	int len;
	int capacity;
};

String* operator+(const String &lhs, const String &rhs);
String* operator+(int lhs, const String &rhs);
String* operator+(const String &lhs, int rhs);
String* operator+(bool lhs, const String &rhs);
String* operator+(const String &lhs, bool rhs);

//namespaces
}
}

#endif



