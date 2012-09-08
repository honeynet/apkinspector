public class Initializer2 {
    static { 
	throw new Error("bad error"); // ERR initializer can't complete normally
    }  
}
