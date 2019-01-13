package a1819.m2ihm.sortirametz.bdd.factory;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.bdd.dao.content_provider.ContentProviderCategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.content_provider.ContentProviderPlaceDAO;
import a1819.m2ihm.sortirametz.bdd.dao.content_provider.ContentProviderUserDAO;
import android.content.Context;

public class ContentProviderDAOFactory extends AbstractDAOFactory {

    ContentProviderDAOFactory(Context context) {
        super(context);
    }

    @Override
    public UserDAO getUserDAO() {
        return new ContentProviderUserDAO(this.context);
    }

    @Override
    public CategoryDAO getCategoryDAO() {
        return new ContentProviderCategoryDAO(this.context);
    }

    @Override
    public PlaceDAO getPlaceDAO() {
        return new ContentProviderPlaceDAO(this.context);
    }
}
