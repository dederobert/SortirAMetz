package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public abstract class AbstractDAOFactory {

    public enum FactoryType {
        SQLite,
        Mariadb;
    }

    protected Context context;

    public AbstractDAOFactory(Context context) {
        this.context = context;
    }

    public abstract DAO<User> getUserDAO();
    public abstract DAO<Category> getCategoryDAO();
    public abstract DAO<Place> getPlaceDAO();

    public static AbstractDAOFactory getFactory(Context context, FactoryType type) {
        if (type.equals(FactoryType.SQLite))
            return new SQLiteDAOFactory(context);
        else if (type.equals(FactoryType.Mariadb))
            return new MariadbDAOFactory(context);
        return null;
    }

}
