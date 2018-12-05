package a1819.m2ihm.sortirametz.helpers;

import a1819.m2ihm.sortirametz.ConsultFragment;
import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.util.Objects;

public enum Logger {
    INSTANCE;

    private final String PREFERENCE_TAG = "logger_prefs";
    private final String LOGIN_FIELD = "login";
    private final String EMAIL_FIELD = "email";
    private final String PASSWORD_FIELD = "password";
    private final String ID_FIELD = "id";

    private boolean logged = false;


    /**
     * Check if the user is logged
     * @return True if logged
     * @deprecated replace by isLogged(Context context)
     */
    @Deprecated
    public boolean isLogged() {
        return this.logged;
    }

    /**
     * Check if the user is logged using SharedPreferences
     * @param context The calling context
     * @return True if logged
     */
    public boolean isLogged(Context context) {
        return logged || context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE).contains(LOGIN_FIELD);
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
        AbstractDAOFactory factory = AbstractDAOFactory.getFactory(context, ConsultFragment.FACTORY_TYPE);
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
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
            sharedPreferences.edit()
                    .putLong(ID_FIELD, user.getId())
                    .putString(LOGIN_FIELD, user.getUsername())
                    .putString(EMAIL_FIELD, user.getEmail())
                    .putString(PASSWORD_FIELD, user.getPassword())
                    .apply();
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

    /**
     * Register a new user if username and email doesn't exists in db
     * @param context The calling context
     * @param username The username
     * @param email The email
     * @param password The password
     * @return True if register
     */
    public boolean register(Context context, String username, String email, String password) {
        UserDAO dao = Objects.requireNonNull(AbstractDAOFactory.getFactory(context, ConsultFragment.FACTORY_TYPE)).getUserDAO();
        User user = new User(username, email, password);
        if (dao.findByUsername(username)!=null || dao.findByEmail(email) != null)
            return false;
        dao.create(user);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putLong(ID_FIELD, user.getId())
                .putString(LOGIN_FIELD, user.getUsername())
                .putString(EMAIL_FIELD, user.getEmail())
                .putString(PASSWORD_FIELD, user.getPassword())
                .apply();
        logged = true;
        return true;
    }

    /**
     * Disconnect the user
     * @param context
     */
    public void disconnect(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .remove(ID_FIELD)
                .remove(LOGIN_FIELD)
                .remove(EMAIL_FIELD)
                .remove(PASSWORD_FIELD)
                .apply();
        this.logged = false;
    }
}
