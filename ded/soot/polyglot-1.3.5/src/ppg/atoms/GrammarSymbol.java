package ppg.atoms;

import ppg.util.*;

public abstract class GrammarSymbol extends GrammarPart implements Equatable
{
	protected String name, label;

	public String getName() {
		return name;	
	}

	public void unparse(CodeWriter cw) {
		cw.begin(0);
		cw.write(name);
		if (label != null)
			cw.write(":" + label);
		cw.end();
	}
	
	public String toString() {
		String result = name;
		if (label != null)
			result += ":" + label;
		return result;
	}
	
}
