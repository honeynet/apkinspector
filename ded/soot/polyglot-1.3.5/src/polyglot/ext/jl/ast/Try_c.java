package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.util.*;
import polyglot.types.*;
import polyglot.visit.*;
import java.util.*;

/**
 * An immutable representation of a <code>try</code> block, one or more
 * <code>catch</code> blocks, and an optional <code>finally</code> block.
 */
public class Try_c extends Stmt_c implements Try
{
    protected Block tryBlock;
    protected List catchBlocks;
    protected Block finallyBlock;

    public Try_c(Position pos, Block tryBlock, List catchBlocks, Block finallyBlock) {
	super(pos);
	this.tryBlock = tryBlock;
	this.catchBlocks = TypedList.copyAndCheck(catchBlocks, Catch.class, true);
	this.finallyBlock = finallyBlock;
    }

    /** Get the try block of the statement. */
    public Block tryBlock() {
	return this.tryBlock;
    }

    /** Set the try block of the statement. */
    public Try tryBlock(Block tryBlock) {
	Try_c n = (Try_c) copy();
	n.tryBlock = tryBlock;
	return n;
    }

    /** Get the catch blocks of the statement. */
    public List catchBlocks() {
	return Collections.unmodifiableList(this.catchBlocks);
    }

    /** Set the catch blocks of the statement. */
    public Try catchBlocks(List catchBlocks) {
	Try_c n = (Try_c) copy();
	n.catchBlocks = TypedList.copyAndCheck(catchBlocks, Catch.class, true);
	return n;
    }

    /** Get the finally block of the statement. */
    public Block finallyBlock() {
	return this.finallyBlock;
    }

    /** Set the finally block of the statement. */
    public Try finallyBlock(Block finallyBlock) {
	Try_c n = (Try_c) copy();
	n.finallyBlock = finallyBlock;
	return n;
    }

    /** Reconstruct the statement. */
    protected Try_c reconstruct(Block tryBlock, List catchBlocks, Block finallyBlock) {
	if (tryBlock != this.tryBlock || ! CollectionUtil.equals(catchBlocks, this.catchBlocks) || finallyBlock != this.finallyBlock) {
	    Try_c n = (Try_c) copy();
	    n.tryBlock = tryBlock;
	    n.catchBlocks = TypedList.copyAndCheck(catchBlocks, Catch.class, true);
	    n.finallyBlock = finallyBlock;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Block tryBlock = (Block) visitChild(this.tryBlock, v);
	List catchBlocks = visitList(this.catchBlocks, v);
	Block finallyBlock = (Block) visitChild(this.finallyBlock, v);
	return reconstruct(tryBlock, catchBlocks, finallyBlock);
    }

    /**
     * Bypass all children when peforming an exception check.
     * exceptionCheck(), called from ExceptionChecker.leave(),
     * will handle visiting children.
     */
    public NodeVisitor exceptionCheckEnter(ExceptionChecker ec)
	throws SemanticException
    {
        ec = (ExceptionChecker) super.exceptionCheckEnter(ec);
        return ec.bypassChildren(this);
    }

    /**
     * Performs exceptionChecking. This is a special method that is called
     * via the exceptionChecker's override method (i.e, doesn't follow the
     * standard model for visitation.  
     *
     * @param ec The ExceptionChecker that was run against the 
     * child node. It contains the exceptions that can be thrown by the try
     * block.
     */
    public Node exceptionCheck(ExceptionChecker ec)
	throws SemanticException
    {
	TypeSystem ts = ec.typeSystem();

	// Visit the try block.
        ec = ec.push();
	Block tryBlock = (Block) visitChild(this.tryBlock, ec);
	// First, get exceptions from the try block.
	SubtypeSet thrown = ec.throwsSet(); 
        SubtypeSet caught = new SubtypeSet(ts.Throwable());
        ec = ec.pop();

	// Add the unchecked exceptions.
	thrown.addAll(ts.uncheckedExceptions());

	// Walk through our catch blocks, making sure that they each can 
	// catch something.
	for (Iterator i = this.catchBlocks.iterator(); i.hasNext(); ) {
	    Catch cb = (Catch) i.next();
	    Type catchType = cb.catchType();

	    // Check if the catch type is a supertype or a subtype of an
	    // exception thrown in the try block.

	    boolean match = false;
	    for (Iterator j = thrown.iterator(); j.hasNext(); ) {
		Type ex = (Type) j.next();

		if (ts.isSubtype(ex, catchType) ||
                    ts.isSubtype(catchType, ex)) {
		    match = true;
		    break;
		}
	    }

	    if (! match) {
		throw new SemanticException("The exception \"" +
		    catchType + "\" is not thrown in the try block.",
		    cb.position()); 
	    }

	    // Check if the exception has already been caught.
	    if (caught.contains(catchType)) {
		throw new SemanticException("The exception \"" +
		    catchType + "\" has been caught by an earlier catch block.",
		    cb.position()); 
	    }

	    caught.add(catchType);
	}

	// Remove exceptions which have been caught.
	thrown.removeAll(caught);

	// "thrown" now contains any exceptions which were not caught.
	// We now visit the catch blocks and finallyBlock to get the
	// exceptions they throw.

	List catchBlocks = new ArrayList(this.catchBlocks.size());

	for (Iterator i = this.catchBlocks.iterator(); i.hasNext(); ) {
	    Catch cb = (Catch) i.next();

            ec = ec.push();

	    cb = (Catch) visitChild(cb, ec);
	    catchBlocks.add(cb);

	    thrown.addAll(ec.throwsSet());
            ec = ec.pop();
	}

	Block finallyBlock = null;

	if (this.finallyBlock != null) {
            ec = ec.push();

	    finallyBlock = (Block) visitChild(this.finallyBlock, ec);

            // an interesting thing happens here...
            // if the finally block can complete normally, then all the
            // exceptions that the try-block and catch-blocks can throw
            // can be thrown by the try-catch-finally block. HOWEVER, if
            // the finally block can not complete normally, then the
            // try-catch-finally block can only throw the exceptions thrown
            // by the finally block. Examining the finally-block's reachability
            // will tell us if the finally-block can complete normally.
            if (!this.finallyBlock.reachable()) {

                // warn the user, and remove all the exceptions that have
                // been added by the try and catch blocks.

                if (false) {
                      // don't warn the user; javac doesn't
                      ec.errorQueue().enqueue(ErrorInfo.WARNING,
                            "The finally block cannot complete normally", 
                            finallyBlock.position());
                }
                
                thrown.clear();
            }
            thrown.addAll(ec.throwsSet());
            
            ec = ec.pop();
	}
    
        // "thrown" now contains any exceptions which were not caught,
        // and any exceptions thrown by the catch blocks and finallyBlock 
        // Add these exceptions to the exception checker's throw set.
        ec.throwsSet().addAll(thrown);

	return reconstruct(tryBlock, catchBlocks, finallyBlock).exceptions(ec.throwsSet());
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("try ");
        sb.append(tryBlock.toString());

        int count = 0;

	for (Iterator it = catchBlocks.iterator(); it.hasNext(); ) {
	    Catch cb = (Catch) it.next();

            if (count++ > 2) {
              sb.append("...");
              break;
            }

            sb.append(" ");
            sb.append(cb.toString());
        }

        if (finallyBlock != null) {
            sb.append(" finally ");
            sb.append(finallyBlock.toString());
        }

        return sb.toString();
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write("try");
	printSubStmt(tryBlock, w, tr);

	for (Iterator it = catchBlocks.iterator(); it.hasNext(); ) {
	    Catch cb = (Catch) it.next();
	    w.newline(0);
	    printBlock(cb, w, tr);
	}

	if (finallyBlock != null) {
	    w.newline(0);
	    w.write ("finally");
	    printSubStmt(finallyBlock, w, tr);
	}
    }

    public Term entry() {
        return tryBlock.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        // Add edges from the try entry to any catch blocks for Error and
        // RuntimeException.
        TypeSystem ts = v.typeSystem();

        CFGBuilder v1 = v.push(this, false);
        CFGBuilder v2 = v.push(this, true);

        for (Iterator i = ts.uncheckedExceptions().iterator(); i.hasNext(); ) {
            Type type = (Type) i.next();
            v1.visitThrow(tryBlock.entry(), type);
        }

        Term next;

        // Handle the normal return case.  The throw case will be handled
        // specially.
        if (finallyBlock == null) {
            next = this;
        }
        else {
            next = finallyBlock.entry();
            v.visitCFG(finallyBlock, this);
        }

        v1.visitCFG(tryBlock, next);

        for (Iterator it = catchBlocks.iterator(); it.hasNext(); ) {
            Catch cb = (Catch) it.next();
            v2.visitCFG(cb, next);
        }

        return succs;
    }
}
