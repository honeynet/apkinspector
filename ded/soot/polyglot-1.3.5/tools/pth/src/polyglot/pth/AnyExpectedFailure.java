/*
 * Author : Stephen Chong
 * Created: Nov 24, 2003
 */
package polyglot.pth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import polyglot.util.ErrorInfo;

/**
 * 
 */
public class AnyExpectedFailure extends ExpectedFailure {
    public AnyExpectedFailure() {
        super(-1);
    }
    public boolean matches(ErrorInfo e) {
        return true;
    }
    
    public boolean equals(Object o) {
        if (o instanceof AnyExpectedFailure) {
            return true;
        }
        return false;
    }
    public int hashCode() {
        return (74298);
    }
    public String toString() {
        return "(any remaining failures)";
    }
}
