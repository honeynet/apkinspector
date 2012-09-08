package ppg.spec;

import java.util.*;
import ppg.*;
import ppg.code.*;
import ppg.parse.*;

public abstract class Spec implements Unparse
{
	protected String packageName;
	protected Vector imports, symbols, prec;
	protected InitCode initCode;
	protected ActionCode actionCode;
	protected ParserCode parserCode;
	protected ScanCode scanCode;
	protected PPGSpec child;
	
	public Spec () {
		initCode = null;
		actionCode = null;
		parserCode = null;
		scanCode = null;
		child = null;
	}
	
	public void setPkgName (String pkgName) {
		if (pkgName != null)
			packageName = pkgName; 
	}
	
	public void replaceCode (Vector codeParts) {
		if (codeParts == null) 
			return;
		
		Code code=null;
		for (int i=0; i < codeParts.size(); i++) {
			try {
				code = (Code) codeParts.elementAt(i);
				if (code instanceof ActionCode && code != null) {
					actionCode = (ActionCode) code.clone();
				} else if (code instanceof InitCode && code != null) {
					initCode = (InitCode) code.clone();
				} else if (code instanceof ParserCode && code != null) {
					parserCode = (ParserCode) code.clone();
				} else { // must be ScanCode
					if (code != null)
						scanCode = (ScanCode) code.clone();
				}
			} catch (Exception e) {
				System.err.println(PPG.HEADER+" Spec::replaceCode(): not a code segment "+
								   "found in code Vector: "+
								   ((code == null) ? "null" : code.toString()));
				System.exit(1);
			}
		}
	}
	
	public void addImports (Vector imp) {
		if (imp == null)
			return;
		
		for (int i=0; i < imp.size(); i++) {
			imports.addElement(imp.elementAt(i));
		}
	}
	
	public void setChild (PPGSpec childSpec) {
		child = childSpec;
	}
	
	// default action is to do nothing: as CUP does
	public void parseChain(String basePath) {}

	/**
	 * Combine the chain of inheritance into one CUP spec
	 */
	public abstract CUPSpec coalesce() throws PPGError;
	
}
