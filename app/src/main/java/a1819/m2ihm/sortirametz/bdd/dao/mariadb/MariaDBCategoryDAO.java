package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.models.Category;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public final class MariaDBCategoryDAO extends MariaDBDAO implements CategoryDAO {

    public MariaDBCategoryDAO(Context context) {
        super(context);
    }

    @Override
    public List<Category> findAll() {
        return null;
    }

    @Nullable
    @Override
    public Category find(long id) {

        return null;
    }

    @Override
    public Category create(@NonNull Category obj) {
        return null;
    }

    @Override
    public void update(@NonNull Category obj) {
    }

    @Override
    public void delete(@NonNull Category obj) {

    }
}
