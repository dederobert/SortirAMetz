package a1819.m2ihm.sortirametz.models;

public interface Recyclerable {

    public enum Type {
        PLACE,
        CATEGORY,
        USER
    }

    Type getType();
}
