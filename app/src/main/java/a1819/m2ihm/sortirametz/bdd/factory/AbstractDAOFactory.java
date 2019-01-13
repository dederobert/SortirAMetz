package a1819.m2ihm.sortirametz.bdd.factory;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import android.content.Context;
import android.support.annotation.NonNull;

public abstract class AbstractDAOFactory {

    public enum FactoryType {
        SQLite,
        Mariadb,
        ContentProvider
    }

    protected Context context;

    AbstractDAOFactory(Context context) {
        this.context = context;
    }

    public abstract UserDAO getUserDAO();
    public abstract CategoryDAO getCategoryDAO();
    public abstract PlaceDAO getPlaceDAO();

    @NonNull
    public static AbstractDAOFactory getFactory(Context context, FactoryType type) {
        switch (type) {
            case ContentProvider: return new ContentProviderDAOFactory(context);
            case Mariadb: return new MariaDBDAOFactory(context);
            default: return new SQLiteDAOFactory(context);
        }
    }

}
