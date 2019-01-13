package a1819.m2ihm.sortirametz.bdd.dao.content_provider;

import a1819.m2ihm.sortirametz.bdd.contract.SortirAMetz;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import a1819.m2ihm.sortirametz.utils.Bdd;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import static a1819.m2ihm.sortirametz.bdd.DataBase.*;

public class ContentProviderPlaceDAO extends ContentProviderDAO implements PlaceDAO {

    public ContentProviderPlaceDAO(Context context) {
        super(context);
    }

    @Override
    public List<Recyclerable> findAllGroupByCategory() {
        return null;
    }

    @NonNull
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
        this.getContentResolver().insert(SortirAMetz.Places.CONTENT_URI, Bdd.INSTANCE.placeToContentValues(obj));
        return obj;
    }

    @Override
    public void update(@NonNull Place obj) {
        this.getContentResolver().update(SortirAMetz.Places.CONTENT_URI, Bdd.INSTANCE.placeToContentValues(obj), SortirAMetz.Places.ID+" = ?", new String[]{String.valueOf(obj.getId())});
    }

    @Override
    public void delete(@NonNull Place obj) {
        this.getContentResolver().delete(SortirAMetz.Places.CONTENT_URI, SortirAMetz.Places.ID+" = ?", new String[]{String.valueOf(obj.getId())});
    }
}
