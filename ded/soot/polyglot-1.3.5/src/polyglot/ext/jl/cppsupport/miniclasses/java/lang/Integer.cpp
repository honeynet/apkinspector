#include"../../../cppsupport.h"
#include<stdio.h>

namespace java {
namespace lang {

Integer::Integer(int i) { val = i; };
Integer::Integer(String *s) {
	val = parseInt(s);
}

int Integer::compareTo(Object *o)
{
	Integer *i = dynamic_cast<Integer*>(o);
	if(NULL == i)
		return -1;

	if(i->val > val)
		return -1;
	if(i->val < val)
		return 1;

	return 0;
}

String *Integer::toString()
{
	return toString(val);
}

int Integer::parseInt(String *s)
{
	if(NULL == s)
		return 0;
	return atoi(s->getBytes());
}

String *Integer::toString(int i)
{
	char buf[64];
	memset(buf, 0, 64);
	sprintf(buf, "%i", i);
	return new String(buf);
}


//namespaces
}
}

