#ifndef _JAVA_IO_FILE
#define _JAVA_IO_FILE

using namespace java::lang;

namespace java {
namespace io {

class File : public Object
{
public:
	File(String *pathname) { this->pathname = pathname; };

	virtual String *getPath() { return pathname; };
	virtual String *toString() { return pathname; };

private:
	String *pathname;

};

//namespaces
}
}

#endif



