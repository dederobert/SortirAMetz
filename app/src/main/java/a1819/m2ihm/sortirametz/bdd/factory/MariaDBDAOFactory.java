package a1819.m2ihm.sortirametz.bdd.factory;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariaDBCategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariaDBPlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariaDBUserDAO;
import android.content.Context;

public class MariaDBDAOFactory extends AbstractDAOFactory {


    MariaDBDAOFactory(Context context) {
        super(context);
    }

    @Override
    public UserDAO getUserDAO() {
        return new MariaDBUserDAO(this.context);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new MariaDBCategoryDAO(this.context);
    }

    @Override
    public PlaceDAO getPlaceDAO() {
        return new MariaDBPlaceDAO(this.context);
    }
}
