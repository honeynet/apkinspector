package ppg.atoms;

import ppg.parse.*;
import ppg.util.*;
public class Terminal extends GrammarSymbol
{
	public Terminal(String name, String label) {
		this.name = name;
		this.label = label;
	}

	public Terminal(String name) {
		this.name = name;
		label = null;
	}

	public Object clone () {
		return new Terminal(name, label);	
	}
	
	public boolean equals(Object o) {
		if (o instanceof Terminal) {
			return name.equals( ((Terminal)o).getName() );	
		} else if (o instanceof String) {
			// do we even need the nonterminal/terminal distinction?
			return name.equals(o);
		}
		return false;
	}
}
