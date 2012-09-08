public class TypeShadowing {
    public TypeShadowing() {
        int TypeShadowing;
        TypeShadowing t = new TypeShadowing();
        t.init(t);
    }

    public void init(TypeShadowing TypeShadowing) {
        TypeShadowing t = new TypeShadowing();
    }
}
