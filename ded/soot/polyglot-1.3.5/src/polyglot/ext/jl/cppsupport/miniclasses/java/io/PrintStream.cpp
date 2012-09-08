#include"../../../cppsupport.h"
#include<iostream>
#include<fstream>

using namespace std;
using namespace java::lang;

namespace java {
namespace io {

PrintStream::PrintStream(File *f)
{
	os = new ofstream(f->getPath()->getBytes(), ios::out);
}

PrintStream::PrintStream(String *fileName)
{
	os = new ofstream(fileName->getBytes(), ios::out);
}

PrintStream::PrintStream(std::ostream &o)
{
	os = &o;
}

void PrintStream::close()
{
	flush();

	ofstream *of = dynamic_cast<ofstream *>(os);
	if(NULL != of)
		of->close();
}

void PrintStream::flush()
{
	os->flush();
}

void PrintStream::write(jmatch_array<char> *b)
{
	for(int i = 0; i < b->length; i++)
		*os << (*b)[i];
}

void PrintStream::write(jmatch_array<char> *b, int off, int len)
{
	for(int i = off; i < b->length && i < off + len; i++)
		*os << (*b)[i];
}

void PrintStream::write(int b)
{
	*os << (char)b;
}

void PrintStream::print(bool b)
{
	if(b)
		*os << "true";
	else
		*os << "false";
}

void PrintStream::print(char c)
{
	*os << c;
}

void PrintStream::print(jmatch_array<char> *s)
{
	write(s);
}

void PrintStream::print(int i)
{
	*os << i;
}

void PrintStream::print(Object *o)
{
	if(NULL == o)
		*os << "(null)";
	String *s = o->toString();
	*os << s->getBytes();
}


void PrintStream::println()
{
	*os << endl;
}

void PrintStream::println(bool b)
{
	print(b);
	println();
}

void PrintStream::println(int i)
{
	print(i);
	println();
}

void PrintStream::println(Object *o)
{
	print(o);
	println();
}

void PrintStream::println(char c)
{
	print(c);
	println();
}

void PrintStream::println(jmatch_array<char> *s)
{
	print(s);
	println();
}

} //io
} //java





