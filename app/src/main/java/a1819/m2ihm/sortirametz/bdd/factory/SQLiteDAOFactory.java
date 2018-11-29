package a1819.m2ihm.sortirametz.bdd.factory;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLiteCategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLitePlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.sqlite.SQLiteUserDAO;
import android.content.Context;

public class SQLiteDAOFactory extends AbstractDAOFactory {

    SQLiteDAOFactory(Context context) {
        super(context);
    }

    @Override
    public UserDAO getUserDAO() {
        return new SQLiteUserDAO(this.context);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new SQLiteCategoryDAO(this.context);
    }

    @Override
    public PlaceDAO getPlaceDAO() {
        return new SQLitePlaceDAO(this.context);
    }
}
