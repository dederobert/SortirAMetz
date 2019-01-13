package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public final class SQLitePlaceDAO extends SQLiteDAO implements PlaceDAO {

    public SQLitePlaceDAO(Context context) {
        super(context);
    }

    @Override
    public List<Place> findAll() {
        return this.dataBase.getAllPlaces();
    }

    @Nullable
    @Override
    public Place find(long id) {
        return this.dataBase.getPlace(id);
    }

    @Override
    public Place create(@NonNull Place obj) {
        return this.dataBase.addPlace(obj);
    }

    @Override
    public void update(@NonNull Place obj) {
        this.dataBase.updatePlace(obj);
    }

    @Override
    public void delete(@NonNull Place obj) {
        this.dataBase.deletePlace(obj);
    }

    @Override
    public List<Recyclerable> findAllGroupByCategory() {
        return this.dataBase.getAllPlacesGroupByCategory();
    }
}
