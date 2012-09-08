package polyglot.util;

/** String utilities. */
public class StringUtil
{
    /**
     * Given the name for a class, returns the portion which appears to
     * constitute the package -- i.e., all characters up to but not including
     * the last dot, or no characters if the name has no dot.
     **/
    public static String getPackageComponent(String fullName) {
	int lastDot = fullName.lastIndexOf('.');
	return lastDot >= 0 ? fullName.substring(0,lastDot) : "";
    }
   
    /**
     * Given the name for a class, returns the portion which appears to
     * constitute the package -- i.e., all characters after the last
     * dot, or all the characters if the name has no dot.
     **/
    public static String getShortNameComponent(String fullName) {
	int lastDot = fullName.lastIndexOf('.');
	return lastDot >= 0 ? fullName.substring(lastDot+1) : fullName;
    }

    /**
     * Returns true iff the provided class name does not appear to be
     * qualified (i.e., it has no dot.)
     **/
    public static boolean isNameShort(String name) {
	return name.indexOf('.') < 0;
    }

    public static String getFirstComponent(String fullName) {
	int firstDot = fullName.indexOf('.');
	return firstDot >= 0 ? fullName.substring(0,firstDot) : fullName;
    }

    public static String removeFirstComponent(String fullName) {
	int firstDot = fullName.indexOf('.');
	return firstDot >= 0 ? fullName.substring(firstDot+1) : "";
    }
 
    public static String escape(String s) {
        return escape(s, false);
    }

    public static String escape(char c) {
        return escape(String.valueOf(c), false);
    }

    public static String unicodeEscape(String s) {
        return escape(s, true);
    }

    public static String unicodeEscape(char c) {
        return escape(String.valueOf(c), true);
    }

    public static String escape(String s, boolean unicode) {
        StringBuffer sb = new StringBuffer(s.length());

	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);
	    escape(sb, c, unicode);
	}

	return sb.toString();
    }

    private static void escape(StringBuffer sb, char c, boolean unicode) {
        if (c > 0xff) {
            if (unicode) {
                String s = Integer.toHexString(c);
                while (s.length() < 4) s = "0" + s;
                sb.append("\\u" + s);
            }
            else {
                sb.append(c);
            }
	    return;
	}

	switch (c) {
	    case '\b': sb.append("\\b"); return;
	    case '\t': sb.append("\\t"); return;
	    case '\n': sb.append("\\n"); return;
	    case '\f': sb.append("\\f"); return;
	    case '\r': sb.append("\\r"); return;
	    case '\"': sb.append("\\" + c); return; // "\\\"";
	    case '\'': sb.append("\\" + c); return; // "\\\'";
	    case '\\': sb.append("\\" + c); return; // "\\\\";
	}

        if (c >= 0x20 && c < 0x7f) {
            sb.append(c);
	    return;
	}

        sb.append("\\" + (char) ('0' + c / 64)
                       + (char) ('0' + (c & 63) / 8)
                       + (char) ('0' + (c & 7)));
    }
    
    public static String nth(int n) {
        StringBuffer s = new StringBuffer(String.valueOf(n));
        if (s.length() > 1) {
            if (s.charAt(s.length()-2) == '1') {
                // all the teens end in "th", e.g. "11th"
                s.append("th");
                return s.toString();                
            }            
        }
        
        char last = s.charAt(s.length()-1);
        switch (last) {
            case '1':
                s.append("st");
                break;
            case '2':
                s.append("nd");
                break;
            case '3':
                s.append("rd");
                break;
            default:
                s.append("th");
                            
        }
        return s.toString();
    }
}
