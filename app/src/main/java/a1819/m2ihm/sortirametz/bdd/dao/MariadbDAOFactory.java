package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariadbCategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariadbPlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.mariadb.MariadbUserDAO;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class MariadbDAOFactory extends AbstractDAOFactory {


    public MariadbDAOFactory(Context context) {
        super(context);
    }

    @Override
    public DAO<User> getUserDAO() {
        return new MariadbUserDAO(this.context);
    }

    @Override
    public DAO<Category> getCategoryDAO() {
        return new MariadbCategoryDAO(this.context);
    }

    @Override
    public DAO<Place> getPlaceDAO() {
        return new MariadbPlaceDAO(this.context);
    }
}
