package a1819.m2ihm.sortirametz.models;

public class Category implements Recyclerable {
    private int id;
    private String description;
    private boolean mockCategory;

    public Category(int id, String description, boolean mockCategory) {
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

    @Override
    public boolean isHeader() {
        return true;
    }

    public boolean isMockCategory() {
        return mockCategory;
    }

    public void setMockCategory(boolean mockCategory) {
        this.mockCategory = mockCategory;
    }
}
