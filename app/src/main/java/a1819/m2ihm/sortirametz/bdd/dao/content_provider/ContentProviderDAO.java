package a1819.m2ihm.sortirametz.bdd.dao.content_provider;

import android.content.ContentResolver;
import android.content.Context;

public class ContentProviderDAO {

    private ContentResolver contentResolver;

    public ContentProviderDAO(Context context) {
        contentResolver = context.getContentResolver();
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    public void setContentResolver(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }
}
