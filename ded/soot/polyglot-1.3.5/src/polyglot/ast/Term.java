package polyglot.ast;

import polyglot.util.SubtypeSet;
import polyglot.visit.*;
import java.util.*;

/**
 * A <code>Term</code> represents any Java expression or statement on which
 * dataflow can be performed.
 */
public interface Term extends Node
{
    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry();

    /**
     * Visit this node, calling calling v.edge() for each successor in succs,
     * if data flows on that edge.
     */
    public List acceptCFG(CFGBuilder v, List succs);
    
    /**
     * Returns true if the term is reachable.  This attribute is not
     * guaranteed correct until after the reachability pass.
     *
     * @see polyglot.visit.ReachChecker
     */
    public boolean reachable();

    /**
     * Set the reachability of this term.
     */
    public Term reachable(boolean reachability);
    
    /**
     * List of Types with all exceptions possibly thrown by this term.
     * The list is not necessarily correct until after exception-checking.
     * <code>polyglot.ast.NodeOps.throwTypes()</code> is similar, but exceptions
     * are not propagated to the containing node.
     */
    public SubtypeSet exceptions();
    public Term exceptions(SubtypeSet exceptions);
}
