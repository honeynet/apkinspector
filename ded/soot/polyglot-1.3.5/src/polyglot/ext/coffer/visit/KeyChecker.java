package polyglot.ext.coffer.visit;

import java.util.*;
import java.util.Map.Entry;

import polyglot.ast.NodeFactory;
import polyglot.ast.ProcedureDecl;
import polyglot.ast.Term;
import polyglot.ext.coffer.Topics;
import polyglot.ext.coffer.extension.CofferExt;
import polyglot.ext.coffer.extension.ProcedureDeclExt_c;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.types.CofferClassType;
import polyglot.ext.coffer.types.CofferProcedureInstance;
import polyglot.ext.coffer.types.CofferTypeSystem;
import polyglot.ext.coffer.types.KeySet;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.DataFlow;
import polyglot.visit.FlowGraph;
import polyglot.visit.FlowGraph.EdgeKey;
import polyglot.visit.FlowGraph.ExceptionEdgeKey;

/**
 * Data flow analysis to compute and check held key sets.
 */
public class KeyChecker extends DataFlow
{
    public KeyChecker(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf, true /* forward analysis */);
        CofferTypeSystem vts = (CofferTypeSystem) ts;
        EMPTY = vts.emptyKeySet(Position.COMPILER_GENERATED);
    }

    public Item createInitialItem(FlowGraph graph, Term node) {
        ProcedureDecl decl = (ProcedureDecl) graph.root();
        CofferProcedureInstance pi = (CofferProcedureInstance)
            decl.procedureInstance();

        CofferClassType t = (CofferClassType) pi.container();

        KeySet held = pi.entryKeys();
        KeySet stored = EMPTY;

        if (t.key() != null) {
            stored = stored.add(t.key());
            stored = stored.retainAll(held);
        }

        return new DataFlowItem(held, held, stored, stored);
    }

    KeySet EMPTY;

    class ExitTermItem extends Item {
        DataFlowItem nonExItem;
        Map excEdgesToItems; // map from ExceptionEdgeKeys to DataFlowItems
        public ExitTermItem(DataFlowItem nonExItem, Map excItems) {
            this.nonExItem = nonExItem;
            this.excEdgesToItems = excItems;
        }

        public boolean equals(Object i) {
            if (i instanceof ExitTermItem) {
                ExitTermItem that = (ExitTermItem)i;
                return this.excEdgesToItems.equals(that.excEdgesToItems) &&
                       this.nonExItem.equals(that.nonExItem);
            }
            return false;
        }

        public int hashCode() {
            return nonExItem.hashCode() + excEdgesToItems.hashCode();
        }
    }

    class DataFlowItem extends Item {
        // keys that must/may be held at this point
        KeySet must_held;
        KeySet may_held;

        // keys that must/may be stored at this point
        KeySet must_stored;
        KeySet may_stored;

        private DataFlowItem() {
            this(EMPTY, EMPTY, EMPTY, EMPTY);
        }

        private DataFlowItem(KeySet must_held, KeySet may_held,
                             KeySet must_stored, KeySet may_stored) {
            this.must_held = must_held;
            this.may_held = may_held;
            this.must_stored = must_stored;
            this.may_stored = may_stored;
        }

        public String toString() {
            return "held_keys(must_held=" + must_held + ", " +
                             "may_held=" + may_held + ", " +
                             "must_stored=" + must_stored + ", " +
                             "may_stored=" + may_stored + ")";
        }

        public boolean equals(Object o) {
            if (o instanceof DataFlowItem) {
                DataFlowItem that = (DataFlowItem) o;
                return this.must_held.equals(that.must_held)
                    && this.may_held.equals(that.may_held)
                    && this.must_stored.equals(that.must_stored)
                    && this.may_stored.equals(that.may_stored);
            }
            return false;
        }

        public int hashCode() {
            return must_held.hashCode() + may_held.hashCode() +
                   must_stored.hashCode() + may_stored.hashCode();
        }
    }

    public Map flow(Item in, FlowGraph graph, Term n, Set succEdgeKeys) {
        if (in instanceof ExitTermItem) {
            return itemToMap(in, succEdgeKeys);
        }

        DataFlowItem df = (DataFlowItem) in;

        if (n.ext() instanceof CofferExt) {
            CofferExt ext = (CofferExt) n.ext();

            Map m = new HashMap();

            for (Iterator i = succEdgeKeys.iterator(); i.hasNext(); ) {
                FlowGraph.EdgeKey e = (FlowGraph.EdgeKey) i.next();
                Type t = null;

                if (e instanceof FlowGraph.ExceptionEdgeKey) {
                    t = ((FlowGraph.ExceptionEdgeKey) e).type();
                }

                KeySet must_held = ext.keyFlow(df.must_held, t);
                KeySet may_held = ext.keyFlow(df.may_held, t);
                KeySet must_stored = ext.keyAlias(df.must_stored, t);
                KeySet may_stored = ext.keyAlias(df.may_stored, t);

                must_stored = must_stored.retainAll(must_held);
                may_stored = may_stored.retainAll(may_held);

                DataFlowItem newdf = new DataFlowItem(must_held, may_held,
                                                      must_stored, may_stored);

                if (Report.should_report(Topics.keycheck, 2)) {
                    Report.report(2, "flow(" + n + "):");
                    Report.report(2, "   " + df);
                    Report.report(2, " ->" + newdf);
                }

                m.put(e, newdf);
            }

            return m;
        }

        return itemToMap(in, succEdgeKeys);
    }

    protected Item safeConfluence(List items, List itemKeys, Term node, FlowGraph graph) {
        if (node == graph.exitNode()) {
            return confluenceExitTerm(items, itemKeys, graph);
        }
        return super.safeConfluence(items, itemKeys, node, graph);
    }

    protected Item confluence(List items, List itemKeys, Term node, FlowGraph graph) {
        if (node == graph.exitNode()) {
            return confluenceExitTerm(items, itemKeys, graph);
        }
        return confluence(items, node, graph);
    }

    protected Item confluenceExitTerm(List items, List itemKeys, FlowGraph graph) {
        List nonExcItems = filterItemsNonException(items, itemKeys);
        DataFlowItem nonExc;

        if (nonExcItems.isEmpty()) {
            nonExc = new DataFlowItem();
        }
        else {
            nonExc = (DataFlowItem)confluence(nonExcItems, graph.exitNode(), graph);
        }

        Map excItemLists = new HashMap();
        for (Iterator i = items.iterator(), j = itemKeys.iterator();
             i.hasNext() && j.hasNext();  ) {
            FlowGraph.EdgeKey key = (EdgeKey)j.next();
            DataFlowItem item = (DataFlowItem)i.next();
            if (key instanceof FlowGraph.ExceptionEdgeKey) {
                List l = (List)excItemLists.get(key);
                if (l == null) {
                        l = new ArrayList();
                        excItemLists.put(key, l);
                }
                l.add(item);
            }
        }

        Map excItems = new HashMap(excItemLists.size());
        for (Iterator i = excItemLists.entrySet().iterator(); i.hasNext(); ) {
                Map.Entry e = (Entry)i.next();
                excItems.put(e.getKey(), confluence((List)e.getValue(), graph.exitNode(), graph));
        }
        return new ExitTermItem(nonExc, excItems);
    }

    protected Item confluence(List inItems, Term node, FlowGraph graph) {
        DataFlowItem outItem = null;

        for (Iterator i = inItems.iterator(); i.hasNext(); ) {
            DataFlowItem df = (DataFlowItem) i.next();

            if (outItem == null) {
                outItem = new DataFlowItem(df.must_held, df.may_held,
                                           df.must_stored, df.may_stored);
                continue;
            }

            outItem.must_held = outItem.must_held.retainAll(df.must_held);
            outItem.may_held = outItem.may_held.addAll(df.may_held);

            outItem.must_stored = outItem.must_stored.retainAll(df.must_stored);
            outItem.may_stored = outItem.may_stored.addAll(df.may_stored);

            outItem.must_stored = outItem.must_stored.retainAll(outItem.must_held);
            outItem.may_stored = outItem.may_stored.retainAll(outItem.may_held);
        }

        if (outItem == null)
            throw new InternalCompilerError("confluence called with insufficient input items.");

        if (Report.should_report(Topics.keycheck, 2)) {
            Report.report(2, "confluence(" + node + "):");

            for (Iterator i = inItems.iterator(); i.hasNext(); ) {
                DataFlowItem df = (DataFlowItem) i.next();
                Report.report(2, "   " + df);
            }

            Report.report(2, " ->" + outItem);
        }

        return outItem;
    }

    public void check(FlowGraph graph, Term n, Item inItem, Map outItems)
        throws SemanticException
    {
        if (n == graph.exitNode()) {
            checkExitTerm(graph, (ExitTermItem)inItem);
        }
        else {
            DataFlowItem df = (DataFlowItem) inItem;
            check(n, df, true);
        }
    }

    private void check(Term n, DataFlowItem df, boolean checkHeldKeys) throws SemanticException {
        if (df == null) {
            return;
        }

        if (Report.should_report(Topics.keycheck, 2)) {
            Report.report(2, "check(" + n + "):");
            Report.report(2, "   " + df);
        }

        if (! df.must_held.containsAll(df.may_held)) {
            KeySet s = df.may_held.removeAll(df.must_held);
            throw new SemanticException("Keys " + s + " may not be held.",
                                        n.position());
        }

        if (! df.must_stored.containsAll(df.may_stored)) {
            KeySet s = df.may_stored.removeAll(df.must_stored);
            throw new SemanticException("Keys " + s + " may not be saved" +
                                        " in a local variable.", n.position());
        }

        if (checkHeldKeys && n.ext() instanceof CofferExt) {
            CofferExt ext = (CofferExt) n.ext();
            ext.checkHeldKeys(df.must_held, df.must_stored);
        }
    }

    private void checkExitTerm(FlowGraph graph, ExitTermItem item)
        throws SemanticException
    {
        check(graph.exitNode(), item.nonExItem, true);

        List excepts;
        ProcedureDeclExt_c ext = null;
        
        if (graph.exitNode() instanceof ProcedureDecl) {
            ProcedureDecl pd = (ProcedureDecl)graph.exitNode();
            CofferProcedureInstance pi = (CofferProcedureInstance)pd.procedureInstance();
            excepts = pi.throwConstraints();
            ext = (ProcedureDeclExt_c)pd.ext();
        }
        else {
            excepts = new ArrayList();
            for (Iterator i = item.excEdgesToItems.keySet().iterator(); i.hasNext(); ) {
                FlowGraph.ExceptionEdgeKey key = (ExceptionEdgeKey)i.next();
                excepts.add(key.type());
            }
        }

        List excKeys = new ArrayList();
        List excItems = new ArrayList();

        for (Iterator i = item.excEdgesToItems.entrySet().iterator(); i.hasNext(); ) {
            Entry e = (Entry)i.next();
            excKeys.add(e.getKey());
            excItems.add(e.getValue());
        }

        for (Iterator i = excepts.iterator(); i.hasNext(); ) {
            Object o = i.next();
            ThrowConstraint tc = null;
            Type excType = null;
            if (o instanceof ThrowConstraint) {
                tc = (ThrowConstraint)o;
                excType = tc.throwType();
            }
            else {
                excType = (Type)o;
            }
            List matchingExc = filterItemsExceptionSubclass(excItems, excKeys, excType);
            if (!matchingExc.isEmpty()) {
                DataFlowItem df = (DataFlowItem)confluence(matchingExc, graph.exitNode(), graph);
                check(graph.exitNode(), df, false);

                if (ext != null && tc != null) {
                    ext.checkHeldKeysThrowConstraint(tc, df.must_held, df.must_stored);
                }

            }
        }

    }
}
