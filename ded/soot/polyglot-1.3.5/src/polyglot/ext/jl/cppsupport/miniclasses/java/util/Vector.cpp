#include"../../../cppsupport.h"

namespace java {
namespace util {

Vector::Vector()
{
	m_size = 0;
	m_capacity = 10;
	elements = new Object *[10];
}

Vector::Vector(int initialCap)
{
	if(initialCap <= 0)
		initialCap = 10;
	m_size = 0;
	m_capacity = initialCap;
	elements = new Object *[initialCap];
}

bool Vector::add(Object *o)
{
	if(m_size == m_capacity)
		enlarge(2 * m_capacity);
	elements[m_size++] = o;
	return true;
}

void Vector::clear()
{
	m_size = 0;
	for(int i = 0; i < m_capacity; i++)
		elements[i] = NULL;
}

bool Vector::equals(Object *o)
{
	Vector *v = dynamic_cast<Vector*>(o);
	if(NULL == v)
		return false;
	if(v->m_size != m_size)
		return false;
	for(int i = 0; i < m_size; i++)
		if(!v->elements[i]->equals(elements[i]))
			return false;
	return true;
}

Object *Vector::firstElement()
{
	if(m_size == 0)
		return NULL;
	return elements[0];
}

Object *Vector::get(int index)
{
	if(index < 0 || index >= m_size)
		throw new ArrayIndexOutOfBoundsException();
	return elements[index];
}

Iterator *Vector::iterator()
{
	return new VectorIterator(this);
}

bool Vector::isEmpty()
{
	return m_size == 0;
}

int Vector::size()
{
	return m_size;
}

jmatch_array<Object*> *Vector::toArray()
{
	jmatch_array<Object *> *ar = new jmatch_array<Object *>(m_size);
	for(int i = 0; i < m_size; i++)
		(*ar)[i] = elements[i];
	return ar;	
}

void Vector::enlarge(int newCapacity)
{
	Object **elements2 = new Object*[newCapacity];
	for(int i = 0; i < m_size; i++)
		elements2[i] = elements[i];
	elements = elements2;
	m_capacity = newCapacity;
}


VectorIterator::VectorIterator(Vector *v)
{
	this->v = v;
	pos = 0;
}

bool VectorIterator::hasNext()
{
	return pos < v->m_size;
}

Object *VectorIterator::next()
{
	return v->elements[pos++];
}





//close namespaces
}
}

