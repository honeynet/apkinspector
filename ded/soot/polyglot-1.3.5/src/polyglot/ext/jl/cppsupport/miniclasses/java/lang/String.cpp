#include"../../../cppsupport.h"
#include<iostream>
#include<stdio.h>

namespace java {
namespace lang {

using java::io::PrintStream;

PrintStream *System::out(new PrintStream(std::cout));
PrintStream *System::err(new PrintStream(std::cerr));

String *makestring(const char *s)
{
	return new String(s);
}

String::String()
{
	data = NULL;
	len = 0;
	capacity = 0;
}

String::String(const char *cs) 
{
	len = strlen(cs);
	capacity = len + 1;

	data = new char[capacity];
	memcpy(data, cs, len);
	data[len] = 0;
}

String::String(const String &s)
{
	len = s.len;
	capacity = s.capacity;
	data = new char[capacity];
	memcpy(data, s.data, capacity);
}

String::String(const String *s)
{
	len = s->len;
	capacity = s->capacity;
	data = new char[capacity];
	memcpy(data, s->data, capacity);
}

String::String(const String *lh, const String *rh)
{
	len = lh->len + rh->len;
	capacity = lh->capacity + rh->capacity;
	data = new char[capacity];
	memset(data, 0, capacity);
	strncpy(data, lh->data, capacity);
	strncat(data, rh->data, capacity);
}

bool String::equals(Object *o)
{
	String *s = dynamic_cast<String*>(o);
	if(NULL == s)
		return false;
	if(len != s->len)
		return false;
	if(strcmp(data, s->data) != 0)
		return false;
	return true;
}

int String::compareTo(Object *o)
{
	String *s = dynamic_cast<String*>(o);
	if(NULL == s)
		return false;
	if(len < s->len)
		return -1;
	if(len > s->len)
		return 1;
	return strcmp(data, s->data);
}


String *operator+(const String &lhs, const String &rhs) {
	return new String(&lhs, &rhs);
}

String *operator+(const String &lhs, int rhs) {
	char buf[16];
	memset(buf, 0, 16);
	sprintf(buf, "%i", rhs);
	return new String(&lhs, makestring(buf));
}

String *operator+(int lhs, const String &rhs) {
	char buf[16];
	memset(buf, 0, 16);
	sprintf(buf, "%i", lhs);
	return new String(makestring(buf), &rhs);
}

String *operator+(const String &lhs, bool rhs) {
	if(rhs)
		return new String(&lhs, makestring("true"));
	else
		return new String(&lhs, makestring("false"));
}

String *operator+(bool lhs, const String &rhs) {
	if(lhs)
		return new String(makestring("true"), &rhs);
	else
		return new String(makestring("false"), &rhs);
}

} //lang
} //java


