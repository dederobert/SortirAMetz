package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public final class MariaDBPlaceDAO extends MariaDBDAO<Place> implements PlaceDAO {
    public MariaDBPlaceDAO(Context context) {
        super(context);
    }

    @Override
    public List<Place> findAll() {
        return null;
    }

    @Nullable
    @Override
    public Place find(long id) {
        return null;
    }

    @Override
    public Place create(@NonNull Place obj) {
        return null;
    }

    @Override
    public Place update(@NonNull Place obj) {
        return null;
    }

    @Override
    public void delete(@NonNull Place obj) {

    }

    @Override
    public List<Recyclerable> findAllGroupByCategory() {
        return null;
    }
}
