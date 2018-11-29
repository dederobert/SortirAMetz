package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public final class MariaDBUserDAO extends MariaDBDAO<User> implements UserDAO {

    public MariaDBUserDAO(Context context) {
        super(context);
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Nullable
    @Override
    public User find(long id) {
        return null;
    }

    @Override
    public User create(@NonNull User obj) {
        return null;
    }

    @Override
    public User update(@NonNull User obj) {
        return null;
    }

    @Override
    public void delete(@NonNull User obj) {

    }

    @Nullable
    @Override
    public User findByUsername(String username) {
        return null;
    }

    @Nullable
    @Override
    public User findByEmail(String email) {
        return null;
    }
}
