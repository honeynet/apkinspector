class Switch7 {
    void foo(int f) {
        switch (f) {
            case 0:
                class Switch7Local {
                };
            case 1:
                // Switch7Local should be in scope.
                // javac incorrectly rejects it.
                Object o = new Switch7Local();
        }
    }
}

