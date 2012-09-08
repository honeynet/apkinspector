public class InterfaceAndObject { 
    public static void main(String[] args) { 
        I i = new I() {}; // OK
    } 
} 
 
interface I { 
    public boolean equals(Object o); 
} 
 
