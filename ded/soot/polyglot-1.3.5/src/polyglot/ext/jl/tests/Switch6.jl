class Switch6 {
    void foo(int f) {
        switch (f) {
            case 0:
                int i = 4;
            case 1:
                // i is in scope
                i = 5;
        }
    }
}

