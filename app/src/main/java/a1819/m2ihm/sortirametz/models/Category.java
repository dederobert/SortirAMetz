package a1819.m2ihm.sortirametz.models;

public class Category implements Recyclerable {
    private long id;
    private String description;
    private boolean mockCategory;

    public Category(long id, String description, boolean mockCategory) {
        this.id = id;
        this.description = description;
        this.mockCategory = mockCategory;
    }

    public Category(String description) {
        this(description, false);
    }

    public Category() {}

    public Category(String description, boolean mockCategory) {
        this(0, description, mockCategory);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    @Override
    public Type getType() {
        return Type.CATEGORY;
    }

    public boolean isMockCategory() {
        return mockCategory;
    }

}
