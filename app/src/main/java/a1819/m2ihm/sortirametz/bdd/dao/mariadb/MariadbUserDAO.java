package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class MariadbUserDAO extends MariadbDAO<User> {

    public MariadbUserDAO(Context context) {
        super(context);
    }

    @Override
    public User find(long id) {
        return null;
    }

    @Override
    public User create(User obj) {
        return null;
    }

    @Override
    public User update(User obj) {
        return null;
    }

    @Override
    public void delete(User obj) {

    }
}
