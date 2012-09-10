/**
 * this is an abstraction to contain all the CPE items
 * that can be created.
 *
 * @see AsciiCP
 * @see ClassCP
 * @see NameTypeCP
 * @see FieldCP
 * @see InterfaceCP
 * @see MethodCP
 * @see IntegerCP
 * @see LongCP
 * @see FloatCP
 * @see DoubleCP
 * @see StringCP
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */


package jas;
                                // one class to ring them all...

import java.io.*;

public abstract class CP
{
  String uniq;

  String getUniq() { return uniq; }

  abstract void resolve(ClassEnv e);

  abstract void write(ClassEnv e, DataOutputStream out)
   throws IOException, jasError;
}
