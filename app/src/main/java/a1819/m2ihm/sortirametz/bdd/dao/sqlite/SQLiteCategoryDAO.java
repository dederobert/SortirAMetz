package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class SQLiteCategoryDAO extends SQLiteDAO implements CategoryDAO {

    public SQLiteCategoryDAO(Context context) {
        super(context);
    }

    @Override
    public List<Category> findAll() {
        return this.dataBase.getAllCategories();
    }

    @Nullable
    @Override
    public Category find(long id) {
        return this.dataBase.getCategory(id);
    }

    @Override
    public Category create(@NonNull Category obj) {
        return this.dataBase.addCategory(obj);
    }

    @Override
    public void update(@NonNull Category obj) {
        this.dataBase.updateCategory(obj);
    }

    @Override
    public void delete(@NonNull Category obj) {
        this.dataBase.deleteCategory(obj);
    }


}
