package a1819.m2ihm.sortirametz.bdd.dao.content_provider;

import a1819.m2ihm.sortirametz.bdd.contract.SortirAMetz;
import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.utils.Bdd;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class ContentProviderCategoryDAO extends ContentProviderDAO implements CategoryDAO {
    public ContentProviderCategoryDAO(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public List<Category> findAll() {
        return Bdd.INSTANCE.cursorToCategories(this.getContentResolver().query(SortirAMetz.Categories.CONTENT_URI, null, null, null, null));
    }

    @Nullable
    @Override
    public Category find(long id) {
        return Bdd.INSTANCE.cursorToCategory(this.getContentResolver().query(SortirAMetz.Categories.CONTENT_URI, null, SortirAMetz.Categories.ID+" = ?", new  String[]{String.valueOf(id)}, null));
    }

    @Override
    public Category create(@NonNull Category obj) {
        ContentValues values = new ContentValues();
        values.put(SortirAMetz.Categories.ID, obj.getId());
        values.put(SortirAMetz.Categories.DESCRIPTION, obj.getDescription());
        this.getContentResolver().insert(SortirAMetz.Categories.CONTENT_URI, values);
        return obj;
    }

    @Override
    public void update(@NonNull Category obj) {
        ContentValues values = new ContentValues();
        values.put(SortirAMetz.Categories.ID, obj.getId());
        values.put(SortirAMetz.Categories.DESCRIPTION, obj.getDescription());

        this.getContentResolver().update(SortirAMetz.Categories.CONTENT_URI, values, SortirAMetz.Categories.ID+" = ?", new String[]{String.valueOf(obj.getId())});
    }

    @Override
    public void delete(@NonNull Category obj) {
        this.getContentResolver().delete(SortirAMetz.Categories.CONTENT_URI, SortirAMetz.Categories.ID+" = ?", new String[]{String.valueOf(obj.getId())});
    }
}
