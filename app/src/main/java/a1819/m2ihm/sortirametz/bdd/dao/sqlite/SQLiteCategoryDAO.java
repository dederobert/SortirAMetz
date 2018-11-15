package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;

public class SQLiteCategoryDAO extends SQLiteDAO<Category> {

    public SQLiteCategoryDAO(Context context) {
        super(context);
    }

    @Override
    public Category find(long id) {
        return null;
    }

    @Override
    public Category create(Category obj) {
        return null;
    }

    @Override
    public Category update(Category obj) {
        return null;
    }

    @Override
    public void delete(Category obj) {

    }
}
