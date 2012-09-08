#include"../../../cppsupport.h"
#include<stdio.h>

namespace java {
namespace lang {

void System::arraycopy(jmatch_array<Object *> *src, int srcOff, 
	jmatch_array<Object *> *tgt, int tgtOff, int len)
{
	int base = tgtOff;
	for(int i = srcOff; i < srcOff + len ; i++)
	{
		(*tgt)[base++] = (*src)[i];
	}
}

//namespaces
}
}

