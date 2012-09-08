class LocalClass4 {
    public static void main(String[] args) {
        class Local {
            { new Local() {}; }
        }
        new Local();
        {
            new Local();
        }
    }
}

