package polyglot.frontend;

import java.io.*;
import polyglot.ast.*;
import polyglot.util.*;

/**
 * A parser interface.  It defines one method, <code>parse()</code>,
 * which returns the root of the AST.
 */
public interface Parser
{
    /** Return the root of the AST */
    Node parse();
}
