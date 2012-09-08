#ifndef _JAVA_UTIL_VECTOR
#define _JAVA_UTIL_VECTOR

namespace java {
namespace util {


class VectorIterator;

class Vector : public Collection
{
public:
	Vector();
	Vector(int initialCap);
	
	virtual bool add(Object *o);
	virtual void clear();
	virtual bool equals(Object *o);
	virtual Object *firstElement();
	virtual Object *get(int index);
	virtual Iterator *iterator();
	virtual bool isEmpty();
	virtual int size();
	virtual jmatch_array<Object * > *toArray();
	virtual String *toString() { return new String("Vector"); }; 

private:
	void enlarge(int newCapacity);

	Object **elements;
	int m_size;
	int m_capacity;
	friend class VectorIterator;
};

class VectorIterator : public Iterator
{
public:
	VectorIterator(Vector *v);

	virtual bool hasNext();
	virtual Object *next();

private:
	int pos;
	Vector *v;
};

//namespaces
}
}

#endif



