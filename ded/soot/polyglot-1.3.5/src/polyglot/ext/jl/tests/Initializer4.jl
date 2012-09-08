public class Initializer4 {
    { 
	try {
	    throw new Exception();
	}
	catch (Exception e) {}
        // OK, initializer finishes normally
	int x;
    }  
}
