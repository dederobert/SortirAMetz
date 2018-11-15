package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class SQLiteUserDAO extends SQLiteDAO<User> {


    public SQLiteUserDAO(Context context) {
        super(context);
    }

    @Override
    public User find(long id) {
        //TODO make this
        return this.dataBase.getUserFromId(id);
    }

    public User findByUsername(String username) {
        return this.dataBase.getUserFromUsername(username);
    }

    public User findByEmail(String email) {
        return this.dataBase.getUserFormEmail(email);
    }

    @Override
    public User create(User obj) {
        return this.dataBase.addUser(obj);
    }

    @Override
    public User update(User obj) {
        return this.dataBase.updateUser(obj);
    }

    @Override
    public void delete(User obj) {
        //TODO make this
    }
}
