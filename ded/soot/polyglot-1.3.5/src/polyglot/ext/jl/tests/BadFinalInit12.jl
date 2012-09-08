class BadFinalInit12 {
    final int x;
    BadFinalInit12() {
	BadFinalInit12 a = this;
	a.x = 0; // bad, even though a is a synonym for this, only acceptable target is this.
    }
}

