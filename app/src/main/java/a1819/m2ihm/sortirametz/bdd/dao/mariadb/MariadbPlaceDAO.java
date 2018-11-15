package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class MariadbPlaceDAO extends MariadbDAO<Place> {
    public MariadbPlaceDAO(Context context) {
        super(context);
    }

    @Override
    public Place find(long id) {
        return null;
    }

    @Override
    public Place create(Place obj) {
        return null;
    }

    @Override
    public Place update(Place obj) {
        return null;
    }

    @Override
    public void delete(Place obj) {

    }
}
