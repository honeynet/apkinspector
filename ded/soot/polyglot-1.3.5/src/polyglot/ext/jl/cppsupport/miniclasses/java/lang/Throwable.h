#ifndef _JAVA_LANG_THROWABLE
#define _JAVA_LANG_THROWABLE

namespace java {
namespace lang {

class Throwable : public Object
{
public:
	Throwable() { msg = new String("Throwable"); };
	Throwable(String *message) { msg = message; };
	Throwable(Throwable *c)
	{
	  cause = c; 
	  if(cause != null)
	    msg = new String(c->getMessage());
	  else
	    msg = new String("null");
	};

	Throwable(String *message, Throwable *c) { msg = message; cause = c; };
	
	String *getMessage() { return msg; };
	String *toString() { return msg; };
	Throwable *getCause() { return cause; };

private:
	String *msg;
	Throwable *cause;
};

//namespaces
}
}

#endif



