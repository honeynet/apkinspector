                                // simple wrapper around
                                // a combination of File/Dataoutputstream

package scm;

import java.io.*;

class scmOutputStream extends DataOutputStream
{
  scmOutputStream(String path)
    throws IOException
  { super(new BufferedOutputStream(new FileOutputStream(path))); }
}
