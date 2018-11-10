package a1819.m2ihm.sortirametz.models;

public class Category {
    private int id;
    private String description;

    public Category(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public Category(String description) {
        this(0, description);
    }

    public Category() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Category) {
            Category category = (Category)obj;
            return this.id==category.getId();
        }
        return false;
    }

    @Override
    public String toString() {
        return description;
    }
}
