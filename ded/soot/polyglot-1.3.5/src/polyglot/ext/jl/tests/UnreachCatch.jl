/*
 * As of Sept 30 2003, the Polyglot compiler incorrectly says 
 * that the second catch block is unreachable.  This disagrees with the 
 * JLS (Section 14.20)
 */
import java.io.*;
import java.util.*;

class C {
    boolean m() {
	try {
	    // can only throw a FileNotFoundException
	    new FileOutputStream("file");
	} 
	catch(FileNotFoundException ee) {
	    return false;
	} 
	catch(IOException ee) {
	    return false; // Polyglot says this is unreachable
	}
	return true;
    }
}
