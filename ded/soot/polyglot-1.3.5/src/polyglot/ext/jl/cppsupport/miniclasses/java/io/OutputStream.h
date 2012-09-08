#ifndef _JAVA_IO_OUTPUTSTREAM
#define _JAVA_IO_OUTPUTSTREAM

using namespace java::lang;

namespace java {
namespace io {

class OutputStream : public Object
{
public:
	OutputStream() { };

	virtual void close() = 0; 
	virtual void flush() = 0;
	virtual void write(jmatch_array<char> *b) = 0;
	virtual void write(jmatch_array<char> *b, int off, int len) = 0;
	virtual void write(int b) = 0;
};

//namespaces
}
}

#endif



