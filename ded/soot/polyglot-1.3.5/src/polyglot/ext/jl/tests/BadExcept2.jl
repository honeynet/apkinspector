// Refers to nonexistant exception in catch clause, with properly spelled
// exception in try-block.

class CorrectlySpelledException extends Exception {}

class BadExcept2 {
  void m() {
    try {
      throw new CorrectlySpelledException();
    } catch (MisspelledException e) {}
  }
}

