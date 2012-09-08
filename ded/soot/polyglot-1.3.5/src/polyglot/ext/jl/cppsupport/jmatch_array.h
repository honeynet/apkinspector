#ifndef _JMATCH_ARRAY_H
#define _JMATCH_ARRAY_H

template<class E> class jmatch_array {
public:
	jmatch_array(int n) {
		length = n;
		array = new E[n];
	}
	jmatch_array(int n, E*ar) {
		length = n;
		array = new E[n];
		for(int i = 0; i < n; i++)
			array[i] = ar[i];
	}

	int length;
	E& operator[](int k) { return array[k]; }

private:
	E *array;
	
};

#include<cstdarg>
template<class E> jmatch_array< E >* makeArray(int sz, ...)
{
        va_list argp;

        va_start(argp, sz);
        if(sz < 0)
                return 0;

        E* ar = new E[sz];

        for(int i = 0; i < sz; i++)
                ar[i] = va_arg(argp, E);

	jmatch_array< E > *jar = new jmatch_array< E >(sz, ar);
        va_end(argp);
	return jar;
}



#endif


