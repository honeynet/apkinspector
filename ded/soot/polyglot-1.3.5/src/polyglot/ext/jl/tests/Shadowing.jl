// A local variable may be shadowed by an inherited member.
// This one fails for the wrong reason: this.i is not considered constant!
class Shadowing {
    void foo(final byte b) {
        class One {
            final int i = 1;
        }
        Object i;
        class Two extends One {
            {
                switch (b) {
                    case 0:
                    case (i == 1) ? 1 : 0:
                }
            }
        }
    }

}

