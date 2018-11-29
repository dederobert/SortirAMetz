package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public final class SQLiteUserDAO extends SQLiteDAO<User> implements UserDAO {

    public SQLiteUserDAO(Context context) {
        super(context);
    }

    @Override
    public List<User> findAll() {
        return this.dataBase.getAllUsers();
    }

    @Override
    public User find(long id) {
        //TODO make this
        return this.dataBase.getUserFromId(id);
    }

    @Nullable
    @Override
    public User findByUsername(String username) {
        return this.dataBase.getUserFromUsername(username);
    }

    @Nullable
    @Override
    public User findByEmail(String email) {
        return this.dataBase.getUserFormEmail(email);
    }

    @Override
    public User create(@NonNull User obj) {
        return this.dataBase.addUser(obj);
    }

    @Override
    public User update(@NonNull User obj) {
        return this.dataBase.updateUser(obj);
    }

    @Override
    public void delete(@NonNull User obj) {
        //TODO make this
    }
}
