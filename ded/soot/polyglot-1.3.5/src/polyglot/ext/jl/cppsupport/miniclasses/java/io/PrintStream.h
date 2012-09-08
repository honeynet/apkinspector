#ifndef _JAVA_IO_PRINTSTREAM
#define _JAVA_IO_PRINTSTREAM

#include<iostream>

using namespace java::lang;

namespace java {
namespace io {

class PrintStream : public OutputStream
{
public:
	PrintStream(File *file);
	PrintStream(String *fileName);
	PrintStream(std::ostream &o);


	virtual void close(); 
	virtual void flush();
	virtual void write(jmatch_array<char> *b);
	virtual void write(jmatch_array<char> *b, int off, int len);
	virtual void write(int b);

	virtual void print(bool b);
	virtual void print(char c);
	virtual void print(jmatch_array<char> *s);
	virtual void print(int i);
	virtual void print(Object *o);

	virtual void println();
	virtual void println(bool b);
	virtual void println(char c);
	virtual void println(jmatch_array<char> *s);
	virtual void println(int i);
	virtual void println(Object *o);

private:
	std::ostream *os;

};

//namespaces
}
}

#endif



