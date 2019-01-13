package a1819.m2ihm.sortirametz.bdd.dao.content_provider;

import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

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
        return null;
    }

    @Override
    public void update(@NonNull Place obj) {

    }

    @Override
    public void delete(@NonNull Place obj) {

    }
}
