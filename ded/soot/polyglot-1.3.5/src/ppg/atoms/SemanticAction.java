package ppg.atoms;

import ppg.parse.*;
import ppg.util.*;
public class SemanticAction extends GrammarPart
{
	private String action;
	
	public SemanticAction (String actionCode) {
		action = actionCode;
	}

	public Object clone() {
		return new SemanticAction(action.toString());	
	}
	
	public void unparse(CodeWriter cw) {
		cw.begin(0);
		cw.write("{:");
		cw.allowBreak(-1);
		cw.write(action);
		cw.allowBreak(0);
		cw.write(":}");
		cw.end();
	}
	
	public String toString () {
		return "{:" + action + ":}\n";
	}
}
