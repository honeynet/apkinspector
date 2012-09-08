class Root { 
    public String toString() { return "root"; } 
} 
class C extends Root implements I {} 

class C1 extends Root implements I1 { 
    public Object clone() { return null; } 
} 

interface I0 { 
    public Object clone(); 
} 

interface I { 
    public boolean equals(Object o); 
} 


interface I1 extends I, I0 { 
}

