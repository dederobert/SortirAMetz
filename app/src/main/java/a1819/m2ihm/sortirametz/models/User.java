package a1819.m2ihm.sortirametz.models;

public class User {

    private long id;
    private String username;
    private String email;
    private String password;

    public User() {}

    public User(String username, String email, String password) {
        this(-1, username, email, password);
    }

    public User(long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "user [id : "+id+", username : "+username+",email : "+email+", hashedPassword :  "+password+"]";
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
