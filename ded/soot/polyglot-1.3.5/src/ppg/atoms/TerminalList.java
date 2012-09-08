package ppg.atoms;

import java.util.*;

public class TerminalList
{
	private String type;
	private Vector symbols;
	
	public TerminalList(String type, Vector syms) {
		this.type = type;
		symbols = syms;
	}	
	
	public String toString() {
		String result = "TERMINAL ";
		if (type != null)
			result += type;
		
		for (int i=0; i < symbols.size(); i++) {
			result += (String)symbols.elementAt(i);
			if (i < symbols.size() - 1)
				result += ", ";
		}
		return result + ";";
	}
}
