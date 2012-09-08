#ifndef _CPPSUPPORT_H
#define _CPPSUPPORT_H

#include<new>

#include<gc_alloc.h>
#include<gc.h>
#include<gc_cpp.h>
inline void * operator new(size_t n)  
	{ return GC_malloc(n); } 
inline void operator delete(void *) {}
inline void * operator new[](size_t n) 
	{ return GC_malloc(n); } 
inline void operator delete[](void *) {}

const int null = 0;

#include"jmatch_array.h"

#include"miniclasses/miniclasses.h"

template<class E> inline bool instanceOf(java::lang::Object *obj)
{
	return (dynamic_cast<E*>(obj) != 0);
}

namespace jmatch_primary { 
}

#endif


