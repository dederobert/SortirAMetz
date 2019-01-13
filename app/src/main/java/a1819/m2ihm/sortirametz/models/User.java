package a1819.m2ihm.sortirametz.models;


public class User implements Recyclerable {

    private long id;
    private String username;
    private String email;
    private String password;
    private String avatar;
    private Gender gender;

    public User() {}

    public User (String username, String email, String password, Gender gender) {
        this(username, email, password, gender, "");
        if (gender.equals(Gender.MAN))
            avatar = "https://cdn4.iconfinder.com/data/icons/user-avatar-flat-icons/512/User_Avatar-31-512.png";
        else
            avatar = "https://www.shareicon.net/download/2016/09/01/822726_user_512x512.png";
    }

    public User(String username, String email, String password, Gender gender, String avatar) {
        this(-1, username, email, password, gender, avatar);
    }

    private User(long id, String username, String email, String password, Gender gender, String avatar) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
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

    @Override
    public Type getType() {
        return Type.USER;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
