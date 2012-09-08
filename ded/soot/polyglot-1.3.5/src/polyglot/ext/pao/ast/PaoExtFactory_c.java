package polyglot.ext.pao.ast;

import polyglot.ast.Ext;
import polyglot.ext.jl.ast.AbstractExtFactory_c;
import polyglot.ext.pao.extension.PaoBinaryExt_c;
import polyglot.ext.pao.extension.PaoCastExt_c;
import polyglot.ext.pao.extension.PaoExt_c;
import polyglot.ext.pao.extension.PaoInstanceofExt_c;

/**
 * Extension factory for the pao extension. The extension factory 
 * is responsible for creating <code>Ext</code> objects, and is
 * used only by the <code>NodeFactory</code>. 
 */
public class PaoExtFactory_c extends AbstractExtFactory_c  {
    PaoExtFactory_c() {
        super();
    }

    /**
     * @return the default Ext object for all AST 
     * nodes other than <code>InstanceOf</code>, 
     * <code>Cast</code> and <code>Binary</code>.
     */
    public Ext extNodeImpl() {
        return new PaoExt_c();
    }

    /**
     * @see AbstractExtFactory_c#extInstanceofImpl()
     */
    public Ext extInstanceofImpl() {
        return new PaoInstanceofExt_c();
    }

    /**
     * @see AbstractExtFactory_c#extCastImpl()
     */
    public Ext extCastImpl() {
        return new PaoCastExt_c();
    }

    /**
     * @see AbstractExtFactory_c#extBinaryImpl()
     */
    public Ext extBinaryImpl() {
        return new PaoBinaryExt_c();
    }
}
