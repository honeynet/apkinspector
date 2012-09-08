package ppg.atoms;

import ppg.util.*;

public class PrecedenceModifier
    extends GrammarPart
    implements Equatable
{
    protected String terminalName;
    public String getTerminalName() { return terminalName; }

    public PrecedenceModifier(String terminalName) {
        this.terminalName = terminalName;
    }

    public Object clone() {
        return new PrecedenceModifier(getTerminalName());
    }

    public void unparse(CodeWriter cw) {
        cw.begin(0);
        cw.write("%prec ");
        cw.write(getTerminalName());
        cw.end();
    }

    public String toString() {
        return "%prec " + getTerminalName();
    }
}
