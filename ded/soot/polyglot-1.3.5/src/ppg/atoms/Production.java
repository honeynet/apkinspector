package ppg.atoms;

import java.util.*;
import ppg.parse.*;
import ppg.util.*;

public class Production implements Unparse
{
	private Nonterminal lhs;
	private Vector rhs;
	private static String HEADER = "ppg [nterm]: ";
	
	public Production (Nonterminal lhs, Vector rhs) {
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public Nonterminal getLHS() { return lhs; }
	public void setLHS(Nonterminal nt) { lhs = nt; }
	public Vector getRHS() { return rhs; }
	
	public Object clone() {
		return new Production((Nonterminal) lhs.clone(),
							  (Vector) rhs.clone());
	}
	
	public void drop (Production prod) {
		//assertSameLHS(prod, "drop");
		Vector toDrop = prod.getRHS();
		// this is O(n^2)
		Vector target, source;
		for (int i=0; i < toDrop.size(); i++) {
			target = (Vector) toDrop.elementAt(i);
			for (int j=0; j < rhs.size(); j++) {
				source = (Vector) rhs.elementAt(j);
				if (isSameProduction(target, source)) {
					rhs.removeElementAt(j);
					break;
				}
				// production match not found
				if (j == rhs.size() - 1) {
					System.err.println(HEADER+"no match found for production:");
					System.err.print(prod.getLHS() + " ::= ");
					for (int k=0; k < target.size(); k++) {
						System.err.print(target.elementAt(k)+" ");	
					}
					System.exit(1);
				}
			}
		}
	}
	
	public static boolean isSameProduction (Vector u, Vector v) {
		int uIdx = 0, vIdx = 0;
		GrammarPart ug = null, vg = null;

		while (uIdx < u.size() && vIdx < v.size()) {
			ug = (GrammarPart) u.elementAt(uIdx);
			if (ug instanceof SemanticAction) {
				uIdx++;
				continue;
			}

			vg = (GrammarPart) v.elementAt(vIdx);
			if (vg instanceof SemanticAction) {
				vIdx++;
				continue;
			}
			
			if (! ug.equals(vg)) 
				return false;
			else {
				uIdx++;
				vIdx++;
			}
		}
		
		if (uIdx == u.size() && vIdx == v.size()) {
			// got through all the way, they are the same
			return true;
		} else {
			// one of the lists was not seen all the way, 
			// must check that only semantic actions are left
			if (uIdx < u.size()) {
				for (; uIdx < u.size(); uIdx++) {
					ug = (GrammarPart) u.elementAt(uIdx);
					if (! (ug instanceof SemanticAction))
						return false;
				}
				return true;
			} else { // vIdx < v.size()
				for (; vIdx < v.size(); vIdx++) {
					vg = (GrammarPart) v.elementAt(vIdx);
					if (! (vg instanceof SemanticAction))
						return false;
				}
				return true;
			}
		}
	}
	
	public void union (Production prod) {
		Vector additional = prod.getRHS();
		union(additional);
	}
	
	public void union (Vector prodList) {
		Vector toAdd, current;
		for (int i=0; i < prodList.size(); i++) {
			toAdd = (Vector) prodList.elementAt(i);
			for (int j=0; j < rhs.size(); j++) {
				current = (Vector) rhs.elementAt(i);
				if (isSameProduction(toAdd, current))
					break;
				if (j == rhs.size() - 1)
					rhs.addElement(toAdd);
			}
		}
	}
	
	public void add (Production prod) {
		//assertSameLHS(prod, "add");
		Vector additional = prod.getRHS();
		for (int i=0; i < additional.size(); i++) {
			rhs.addElement( additional.elementAt(i) );	
		}
	}
	public void addToRHS (Vector rhsPart) {
		rhs.addElement(rhsPart);
	}
	
	private void assertSameLHS(Production prod, String function) {
		if (! (prod.getLHS().equals(lhs)) ) {
			System.err.println(HEADER + "nonterminals do not match in Production."+
							   function + "(): current is "+lhs+", given: "+
							   prod.getLHS());
			System.exit(1);
		}
	}
	public void unparse (CodeWriter cw) {
		cw.begin(0);
		cw.write(lhs.toString() + " ::=");
		cw.allowBreak(3);
		Vector rhs_part;
		for (int i=0; i < rhs.size(); i++) {
			rhs_part = (Vector) rhs.elementAt(i);
			for (int j=0; j < rhs_part.size(); j++) {
				cw.write(" ");
				((GrammarPart) rhs_part.elementAt(j)).unparse(cw);
			}
			if (i < rhs.size() - 1) {
				cw.allowBreak(0);
				cw.write(" | ");
			}			
		}
		cw.write(";");
		cw.newline(); cw.newline();
	
		cw.end();
	}
	
	public String toString() {
		String result = lhs.toString();
		Vector rhs_part;
		result += " ::=";
		for (int i=0; i < rhs.size(); i++) {
			rhs_part = (Vector) rhs.elementAt(i);
			for (int j=0; j < rhs_part.size(); j++) {
				result += " " + rhs_part.elementAt(j).toString();
			}
			if (i < rhs.size() - 1)
				result += " | ";
		}
		return result + ";";
	}
}
