class LocalClass5 {
    public static void main(String[] args) {
        class Local {}
        {
            new Object() {
                class Local {}
            };
        }
        new Object() {
            class Local {}
        };
    }
}

