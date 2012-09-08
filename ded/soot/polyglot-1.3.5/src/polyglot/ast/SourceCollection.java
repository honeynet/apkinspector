package polyglot.ast;

import java.util.List;

/**
 * A <code>SourceCollection</code> represents a collection of source files.
 * This node should be used only during AST rewriting, just before code
 * generation in order to generate multiple target files from a single
 * AST.
 */
public interface SourceCollection extends Node
{
    /** List of source files in the collection.
     * @return A list of {@link polyglot.ast.SourceFile SourceFile}.
     */
    List sources();

    /** Set the list of source files in the collection.
     * @param sources A list of {@link polyglot.ast.SourceFile SourceFile}.
     */
    SourceCollection sources(List sources);
}
