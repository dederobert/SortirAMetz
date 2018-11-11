package a1819.m2ihm.sortirametz.helpers;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.User;
import android.support.annotation.NonNull;

import java.security.SecureRandom;

public enum Logger {
    INSTANCE;

    private boolean logged = false;

    //TODO save logged in local file
    public boolean isLogged() {
        return this.logged;
    }

    /**
     * Try to login application
     * @param db The database instance
     * @param usernameEmail The username or email
     * @param password The password
     * @return True if log successfully false either
     */
    public boolean login(@NonNull DataBase db, @NonNull String usernameEmail,@NonNull String password) {
        User user;
        if (usernameEmail.contains("@"))
            user = db.getUserFormEmail(usernameEmail);
        else
            user = db.getUserFromUsername(usernameEmail);


        if (user!=null) {
            //TODO hash password
            if (user.getPassword().equals(password)){
                this.logged = true;
                return true;
            }
        }
        return false;
    }

    public boolean register(DataBase dataBase, String username, String email, String password) {
        User user = new User(username, email, password);
        if (dataBase.getUserFormEmail(email)!=null || dataBase.getUserFromUsername(username)!=null)
            return false;

        dataBase.addUser(user);
        logged = true;
        return true;
    }
}
