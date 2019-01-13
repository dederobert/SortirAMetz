package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.helpers.Logger;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SQLiteUserDAO extends SQLiteDAO implements UserDAO {

    public SQLiteUserDAO(@NonNull Context context) {
        super(context);
    }

    @Override
    public List<User> findAll() {
        return this.dataBase.getAllUsers();
    }

    @Override
    public User find(long id) {
        return this.dataBase.getUserFromId(id);
    }

    @Nullable
    @Override
    public User findByUsername(@NonNull String username) {
        return this.dataBase.getUserFromUsername(username);
    }

    @Nullable
    @Override
    public User findByEmail(@NonNull String email) {
        return this.dataBase.getUserFormEmail(email);
    }

    @NonNull
    @Override
    public List<User> findAllFriend(@NonNull User user) {
        return this.dataBase.getAllFriendByUser(user);
    }

    @NotNull
    @Override
    public List<User> findAllOther() {
        return this.dataBase.getAllUserWithout(Logger.INSTANCE.getUser());
    }

    @Override
    public User create(@NonNull User obj) {
        return this.dataBase.addUser(obj);
    }

    @Override
    public void update(@NonNull User obj) {
        this.dataBase.updateUser(obj);
    }

    @Override
    public void delete(@NonNull User obj) {
        //TODO make this
    }
}
