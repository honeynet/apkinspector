public class Initializer1 {
    { 
	throw new Error("bad error"); // ERR initializer can't complete normally
    }  
}
