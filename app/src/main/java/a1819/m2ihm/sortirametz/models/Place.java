package a1819.m2ihm.sortirametz.models;

public class Place implements Recyclerable {
    private long id;
    private String name;
    private float latitude;
    private float longitude;
    private String address;
    private Category category;
    private String description;
    private String icon;


    public Place(int id, String name, float latitude, float longitude, String address,
                 Category category, String description, String icon) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.category = category;
        this.description = description;
        this.icon = icon;
    }

    public Place(String name, float latitude, float longitude, String address,
                 Category category, String description, String icon) {
        this(0,name, latitude, longitude, address, category, description, icon);
    }

    public Place() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        if (latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("Latitude must be between -90 and 90, current value is :"+latitude);
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        if (longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("Longitude must be between -180 and 180, current value is :"+longitude);
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "place [id: "+id+",name: "+name+",latitude: "+latitude+",longitude: "+
                longitude+",address: "+address+", "+category.toString()+",description: "+description+"]";
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
