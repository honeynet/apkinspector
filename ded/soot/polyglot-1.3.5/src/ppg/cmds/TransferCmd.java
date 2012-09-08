package ppg.cmds;

import java.util.*;
import ppg.atoms.*;
import ppg.util.*;

public class TransferCmd implements Command
{
	private Nonterminal nonterminal;
	private Vector transferList;
	
	public TransferCmd(String nt, Vector tlist) {
		nonterminal = new Nonterminal(nt);
		transferList = tlist;
	}

	public Nonterminal getSource() { return nonterminal; }
	public Vector getTransferList() { return transferList; }
	
	public void unparse(CodeWriter cw) {
		//cw.begin(0);
		cw.write("TransferCmd");
		cw.allowBreak(2);
		cw.write(nonterminal + " to ");
		Production prod;
		for (int i=0; i < transferList.size(); i++) {
			prod = (Production) transferList.elementAt(i);
			prod.unparse(cw);
		}
		//cw.end();
	}
	
}
