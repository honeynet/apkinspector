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
public class ExpectedFailure {
    public ExpectedFailure(int kind) {
        this(kind, null);
    }
    public ExpectedFailure(String errMsg) {
        this(null, errMsg);
    }
    public ExpectedFailure(int kind, String errMsg) {
        this(new Integer(kind), errMsg);
    }
    public ExpectedFailure(Integer kind, String errMsg) {
        this.kind = kind;
        this.errMsgRegExp = errMsg;
    }
    final Integer kind;
    final String errMsgRegExp;
    
    public boolean matches(ErrorInfo e) {
        if (kind != null && kind.intValue() != e.getErrorKind()) {
            return false;
        }
        if (errMsgRegExp != null) {
            Matcher m = Pattern.compile(errMsgRegExp).matcher(e.getMessage());
            return m.find();
        }
        return true;
    }
    
    public boolean equals(Object o) {
        if (o instanceof ExpectedFailure) {
            ExpectedFailure that = (ExpectedFailure)o;
            return (that.kind == this.kind || (that.kind != null && that.kind.equals(this.kind))) && 
                (that.errMsgRegExp == this.errMsgRegExp ||
            (that.errMsgRegExp != null && that.errMsgRegExp.equals(this.errMsgRegExp)));
        }
        return false;
    }
    public int hashCode() {
        return (errMsgRegExp==null ? -323
                                   : errMsgRegExp.hashCode()) 
              + (kind==null ? 41 : kind.hashCode());
    }
    public String toString() {
        StringBuffer sb = new StringBuffer();
        if (kind != null) {
            sb.append(ErrorInfo.getErrorString(kind.intValue()));
        }
        else {
            sb.append("error");
        }
        if (errMsgRegExp != null) {
            sb.append(" matching the regular expression '");
            sb.append(errMsgRegExp);
            sb.append('\'');
        }
        return sb.toString();
    }
}
