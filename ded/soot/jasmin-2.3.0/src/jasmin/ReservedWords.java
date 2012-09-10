/* --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
 > File:        jasmin/src/jasmin/ReservedWords.java
 > Purpose:     Reserved words for Jasmin
 > Author:      Jonathan Meyer, 10 July 1996
 */

package jasmin;

import java.util.Hashtable;
import java_cup.runtime.*;

abstract class ReservedWords {
    static Hashtable reserved_words;

    // we can't pull this hashtable trick anymore, no more recycling allowed!
    public static Symbol get(String name) {
        Symbol template = (Symbol)reserved_words.get(name);
        if (template != null)
            return new Symbol(template.sym);
        else
            return null;
    }

    public static boolean contains(String name) {
    	return reserved_words.get(name) != null;
    }

    //
    // scanner initializer - sets up reserved_words table
    //
    static {
        reserved_words = new Hashtable();

        // Jasmin directives
        reserved_words.put(".catch", new Symbol(sym.DCATCH));
        reserved_words.put(".class", new Symbol(sym.DCLASS));
        reserved_words.put(".end", new Symbol(sym.DEND));
        reserved_words.put(".field", new Symbol(sym.DFIELD));
        reserved_words.put(".implements", new Symbol(sym.DIMPLEMENTS));
        reserved_words.put(".interface", new Symbol(sym.DINTERFACE));
        reserved_words.put(".limit", new Symbol(sym.DLIMIT));
        reserved_words.put(".line", new Symbol(sym.DLINE));
        reserved_words.put(".method", new Symbol(sym.DMETHOD));
        reserved_words.put(".set", new Symbol(sym.DSET));
        reserved_words.put(".source", new Symbol(sym.DSOURCE));
        reserved_words.put(".super", new Symbol(sym.DSUPER));
        reserved_words.put(".no_super", new Symbol(sym.DNOSUPER));
        reserved_words.put(".throws", new Symbol(sym.DTHROWS));
        reserved_words.put(".var", new Symbol(sym.DVAR));
        
	reserved_words.put(".class_attribute", new Symbol(sym.DCLASS_ATTR));	
        reserved_words.put(".field_attribute", new Symbol(sym.DFIELD_ATTR));
        reserved_words.put(".method_attribute", new Symbol(sym.DMETHOD_ATTR));
        reserved_words.put(".code_attribute", new Symbol(sym.DCODE_ATTR));
        reserved_words.put(".inner_class_attr", new Symbol(sym.DINNER_CLASS_ATTR));	
        reserved_words.put(".inner_class_spec_attr", new Symbol(sym.DINNER_CLASS_SPEC_ATTR));	
        reserved_words.put(".synthetic", new Symbol(sym.DSYNTHETIC));	
        reserved_words.put(".enclosing_method_attr", new Symbol(sym.DENCLOSING_METH));	
        reserved_words.put(".deprecated", new Symbol(sym.DDEPRECATED));	
        reserved_words.put(".signature_attr", new Symbol(sym.DSIG_ATTR));	
        
        // annotation related directives
        reserved_words.put(".runtime_visible_annotation", new Symbol(sym.DRUNTIME_VISIBLE));
        reserved_words.put(".runtime_invisible_annotation", new Symbol(sym.DRUNTIME_INVISIBLE));
        reserved_words.put(".runtime_param_visible_annotation", new Symbol(sym.DRUNTIME_PARAM_VISIBLE));
        reserved_words.put(".runtime_param_invisible_annotation", new Symbol(sym.DRUNTIME_PARAM_INVISIBLE));
        reserved_words.put(".annotation_attr", new Symbol(sym.DANNOTATION_ATTR));
        reserved_words.put(".param", new Symbol(sym.DPARAM_ANNOT_ATTR));
        reserved_words.put(".annotation", new Symbol(sym.DANNOTATION));
        reserved_words.put(".int_kind", new Symbol(sym.INT_KIND));
        reserved_words.put(".byte_kind", new Symbol(sym.BYTE_KIND));
        reserved_words.put(".char_kind", new Symbol(sym.CHAR_KIND));
        reserved_words.put(".short_kind", new Symbol(sym.SHORT_KIND));
        reserved_words.put(".bool_kind", new Symbol(sym.BOOL_KIND));
        reserved_words.put(".str_kind", new Symbol(sym.STR_KIND));
        reserved_words.put(".long_kind", new Symbol(sym.LONG_KIND));
        reserved_words.put(".doub_kind", new Symbol(sym.DOUB_KIND));
        reserved_words.put(".float_kind", new Symbol(sym.FLOAT_KIND));
        reserved_words.put(".enum_kind", new Symbol(sym.ENUM_KIND));
        reserved_words.put(".ann_kind", new Symbol(sym.ANN_KIND));
        reserved_words.put(".arr_kind", new Symbol(sym.ARR_KIND));
        reserved_words.put(".cls_kind", new Symbol(sym.CLS_KIND));
        reserved_words.put(".arr_elem", new Symbol(sym.DARR_ELEM));
        reserved_words.put(".annot_elem", new Symbol(sym.DANNOT_ELEM));
        reserved_words.put(".elem", new Symbol(sym.DELEM));
        reserved_words.put(".annotation_default", new Symbol(sym.DANNOT_DEFAULT));
        
        
        // reserved_words used in Jasmin directives
        reserved_words.put("from", new Symbol(sym.FROM));
        reserved_words.put("method", new Symbol(sym.METHOD));
        reserved_words.put("to", new Symbol(sym.TO));
        reserved_words.put("is", new Symbol(sym.IS));
        reserved_words.put("using", new Symbol(sym.USING));

        // Special-case instructions
        reserved_words.put("tableswitch", new Symbol(sym.TABLESWITCH));
        reserved_words.put("lookupswitch", new Symbol(sym.LOOKUPSWITCH));
        reserved_words.put("default", new Symbol(sym.DEFAULT));

        // Access flags
        reserved_words.put("public", new Symbol(sym.PUBLIC));
        reserved_words.put("private", new Symbol(sym.PRIVATE));
        reserved_words.put("protected", new Symbol(sym.PROTECTED));
        reserved_words.put("static", new Symbol(sym.STATIC));
        reserved_words.put("final", new Symbol(sym.FINAL));
        reserved_words.put("synchronized", new Symbol(sym.SYNCHRONIZED));
        reserved_words.put("volatile", new Symbol(sym.VOLATILE));
        reserved_words.put("transient", new Symbol(sym.TRANSIENT));
        reserved_words.put("native", new Symbol(sym.NATIVE));
        reserved_words.put("interface", new Symbol(sym.INTERFACE));
        reserved_words.put("abstract", new Symbol(sym.ABSTRACT));
        reserved_words.put("strictfp", new Symbol(sym.STRICTFP));
        reserved_words.put("annotation", new Symbol(sym.ANNOTATION));
        reserved_words.put("enum", new Symbol(sym.ENUM));
    }
}
