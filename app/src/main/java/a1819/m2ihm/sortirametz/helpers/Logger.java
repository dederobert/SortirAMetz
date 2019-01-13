package a1819.m2ihm.sortirametz.helpers;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.Gender;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public enum Logger {
    INSTANCE;

    private static final String TIMEOUT_FIELD = "timeout";
    //Timeout en jours
    private static final int DEFAULT_TIMEOUT = 2;
    private static final String LOGGED_FIELD = "logged";
    private final String PREFERENCE_TAG = "logger_prefs";
    private final String LOGIN_FIELD = "login";
    private final String EMAIL_FIELD = "email";
    private final String PASSWORD_FIELD = "password";
    private final String ID_FIELD = "id";

    private boolean logged = false;
    private User user = null;


    /**
     * Check if the user is logged
     * @return True if logged
     * @deprecated replace by isLogged(Context activity)
     */
    @Deprecated
    public boolean isLogged() {
        return this.logged;
    }

    /**
     * Check if the user is logged using SharedPreferences
     * @param context The calling activity
     * @return True if logged
     */
    public boolean isLogged(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        //Si le timeout est passé on deconnecte l'utilisateur
        if (sharedPreferences.contains(TIMEOUT_FIELD)
                && new Date(sharedPreferences.getLong(TIMEOUT_FIELD, 0)).before(new Date())) {
            disconnect(context);
        }
        return logged || sharedPreferences.getBoolean(LOGGED_FIELD, false);
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
     * @param context The current activity
     * @param usernameEmail The user username or email
     * @param password the user password
     * @return True if log successfully
     */
    public boolean login(@NonNull Context context, @NonNull String usernameEmail, @NonNull String password) {
        AbstractDAOFactory factory = AbstractDAOFactory.getFactory(context, ValueHelper.INSTANCE.getFactoryType());
        assert factory != null;
        UserDAO dao = factory.getUserDAO();

        if (usernameEmail.contains("@"))
            user = dao.findByEmail(usernameEmail);
        else
            user = dao.findByUsername(usernameEmail);

        if (user == null) {
            return false;
        }
        //TODO hash password
        if (user.getPassword().equals(password)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, DEFAULT_TIMEOUT);
            sharedPreferences.edit()
                    .putBoolean(LOGGED_FIELD, true)
                    .putLong(ID_FIELD, user.getId())
                    .putLong(TIMEOUT_FIELD, calendar.getTime().getTime())
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
     * @deprecated replace by boolean register(activity, username, email, password)
     * @param dataBase The db
     * @param username The username
     * @param email The email
     * @param password The password
     * @return True if register
     */
    @Deprecated
    public boolean register(@NonNull DataBase dataBase, String username, String email, String password, Gender gender) {
        user = new User(username, email, password, gender);
        if (dataBase.getUserFormEmail(email)!=null || dataBase.getUserFromUsername(username)!=null)
            return false;
        dataBase.addUser(user);
        logged = true;
        return true;
    }

    /**
     * Register a new user if username and email doesn't exists in db
     * @param context The calling activity
     * @param username The username
     * @param email The email
     * @param password The password
     * @return True if register
     */
    public boolean register(@NonNull Context context, String username, String email, String password, Gender gender) {
        UserDAO dao = Objects.requireNonNull(AbstractDAOFactory.getFactory(context, ValueHelper.INSTANCE.getFactoryType())).getUserDAO();
        user = new User(username, email, password, gender);
        if (dao.findByUsername(username)!=null || dao.findByEmail(email) != null)
            return false;
        dao.create(user);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, DEFAULT_TIMEOUT);
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putLong(ID_FIELD, user.getId())
                .putBoolean(LOGGED_FIELD, true)
                .putLong(TIMEOUT_FIELD, calendar.getTime().getTime())
                .putString(LOGIN_FIELD, user.getUsername())
                .putString(EMAIL_FIELD, user.getEmail())
                .putString(PASSWORD_FIELD, user.getPassword())
                .apply();
        logged = true;
        return true;
    }

    public void loadUser(@NonNull  Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        if (sharedPreferences.contains(LOGIN_FIELD)) {
            user = Objects.requireNonNull(AbstractDAOFactory.getFactory(context, ValueHelper.INSTANCE.getFactoryType()))
                    .getUserDAO().findByUsername(sharedPreferences.getString(LOGIN_FIELD, ""));
        }
        Log.d("LOGGER", "User logged "+ this.user);
    }
    /**
     * Disconnect the user
     * @param context The context
     */
    public void disconnect(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_TAG, Context.MODE_PRIVATE);
        sharedPreferences.edit()
                .putBoolean(LOGGED_FIELD, false)
                .apply();
        this.logged = false;
    }

    public User getUser() {
        return user;
    }
}
