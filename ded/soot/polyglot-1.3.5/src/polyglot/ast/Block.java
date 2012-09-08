package polyglot.ast;

import java.util.List;

/**
 * A <code>Block</code> represents a Java block statement -- an immutable
 * sequence of statements.
 */
public interface Block extends CompoundStmt
{
    /**
     * Get the statements in the block.
     * @return A list of {@link polyglot.ast.Stmt Stmt}.
     */
    List statements();

    /**
     * Set the statements in the block.
     * @param statements A list of {@link polyglot.ast.Stmt Stmt}.
     */
    Block statements(List statements);

    /**
     * Append a statement to the block, returning a new block.
     */
    Block append(Stmt stmt);

    /**
     * Prepend a statement to the block, returning a new block.
     */
    Block prepend(Stmt stmt);
}
