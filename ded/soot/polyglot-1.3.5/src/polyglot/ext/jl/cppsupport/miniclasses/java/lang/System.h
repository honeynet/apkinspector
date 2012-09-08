#ifndef _JAVA_LANG_SYSTEM
#define _JAVA_LANG_SYSTEM

#include<stdlib.h>


namespace java {
namespace lang {

using namespace java::io;

class System : public Object
{
private:
	System() { };

public:
	static void arraycopy(jmatch_array<Object *> *tgt, int tgtOff, 
		jmatch_array<Object *> *src, int srcOff, int len);
	static PrintStream *out;
	static PrintStream *err;

	static void exit(int status) { ::exit(status); };
	
	
};

//namespaces
}
}

#endif



