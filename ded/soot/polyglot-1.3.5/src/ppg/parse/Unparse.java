package ppg.parse;

import ppg.util.*;

public interface Unparse { 
    /**
     * Write a human-readable representation of the parse tree
     */
    public void unparse(CodeWriter cw); 
} 

