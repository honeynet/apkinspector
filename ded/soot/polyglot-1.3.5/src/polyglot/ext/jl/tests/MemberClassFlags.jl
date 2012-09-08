import java.awt.geom.*;

/**
 * Test if the member class flags are set correctly when loaded from a class
 * file.  The flags are found in the InnerClasses attribute of the container
 * class, not in the modifiers field of the member class.
 * If the modifiers field in the member class are used, this test case
 * should fail.
 */
class MemberClassFlags {
    void f() {
          Object o = new Rectangle2D.Float(0,0,0,0);
    }
}
