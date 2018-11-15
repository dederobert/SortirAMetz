package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLiteCategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLitePlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLiteUserDAO;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class SQLiteDAOFactory extends AbstractDAOFactory {

    public SQLiteDAOFactory(Context context) {
        super(context);
    }

    @Override
    public DAO<User> getUserDAO() {
        return new SQLiteUserDAO(this.context);
    }

    @Override
    public DAO<Category> getCategoryDAO() {
        return new SQLiteCategoryDAO(this.context);
    }

    @Override
    public DAO<Place> getPlaceDAO() {
        return new SQLitePlaceDAO(this.context);
    }
}
