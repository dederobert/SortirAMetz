package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class SQLitePlaceDAO extends SQLiteDAO<Place> {
    public SQLitePlaceDAO(Context context) {
        super(context);
    }

    @Override
    public Place find(long id) {
        return this.dataBase.getPlace(id);
    }

    @Override
    public Place create(Place obj) {
        return this.dataBase.addPlace(obj);
    }

    @Override
    public Place update(Place obj) {
        return this.dataBase.updatePlace(obj);
    }

    @Override
    public void delete(Place obj) {
        this.dataBase.deletePlace(obj);
    }
}
