package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import java.util.*;

/**
 * An ambiguous key set AST node.  This is essentially a list of possibly
 * ambiguous key nodes.
 */
public interface AmbKeySetNode extends KeySetNode
{
    public List keyNodes();
    public AmbKeySetNode keyNodes(List keyNodes);
}
