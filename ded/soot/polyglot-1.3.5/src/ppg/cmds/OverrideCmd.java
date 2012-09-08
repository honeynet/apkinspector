package ppg.cmds;

import ppg.atoms.*;
import ppg.util.*;

public class OverrideCmd implements Command
{
	private Production prod;
	
	public OverrideCmd(Production p) 
	{
		prod = p;
	}

	public Nonterminal getLHS() { return prod.getLHS(); }
	public Production getProduction() { return prod; }
	
	public void unparse(CodeWriter cw) {
		//cw.begin(0);
		cw.write("OverrideCmd");
		cw.allowBreak(0);
		prod.unparse(cw);
		//cw.end();
	}
	
}
