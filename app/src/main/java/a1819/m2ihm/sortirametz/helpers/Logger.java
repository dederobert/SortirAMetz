package a1819.m2ihm.sortirametz.helpers;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;

import java.util.Objects;

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
     * @deprecated replace by boolean login(Context, username/Email, password)
     * @return True if log successfully false either
     */
    @Deprecated
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

    /**
     * Try to login with given username or email and password
     * @param context The current context
     * @param usernameEmail The user username or email
     * @param password the user password
     * @return True if log successfully
     */
    public boolean login(@NonNull Context context, @NonNull String usernameEmail, @NonNull String password) {
        AbstractDAOFactory factory = AbstractDAOFactory.getFactory(context, ConsultActivity.FACTORY_TYPE);
        assert factory != null;
        UserDAO dao = factory.getUserDAO();
        User user;

        if (usernameEmail.contains("@"))
            user = dao.findByEmail(usernameEmail);
        else
            user = dao.findByUsername(usernameEmail);

        assert user != null;
        //TODO hash password
        if (user.getPassword().equals(password)) {
            this.logged = true;
            return true;
        }
        return false;
    }

    /**
     * Register a new user if username and email doesn't exists in db
     * @deprecated replace by boolean register(context, username, email, password)
     * @param dataBase The db
     * @param username The username
     * @param email The email
     * @param password The password
     * @return True if register
     */
    @Deprecated
    public boolean register(DataBase dataBase, String username, String email, String password) {
        User user = new User(username, email, password);
        if (dataBase.getUserFormEmail(email)!=null || dataBase.getUserFromUsername(username)!=null)
            return false;

        dataBase.addUser(user);
        logged = true;
        return true;
    }

    public boolean register(Context context, String username, String email, String password) {
        UserDAO dao = Objects.requireNonNull(AbstractDAOFactory.getFactory(context, ConsultActivity.FACTORY_TYPE)).getUserDAO();
        User user = new User(username, email, password);
        if (dao.findByUsername(username)!=null || dao.findByEmail(email) != null)
            return false;
        dao.create(user);
        logged = true;
        return true;
    }
}
