package a1819.m2ihm.sortirametz.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class ContentProvider extends android.content.ContentProvider {

    DataBase dataBase;

    @Override
    public boolean onCreate() {
        dataBase = new DataBase(this.getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dataBase.getReadableDatabase();
        String tableName = uri.getPath().substring(1);
        return db.query(tableName, projection, selection, selectionArgs, null, null, null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = uri.getPath().substring(1);
        dataBase.getWritableDatabase().insert(tableName, null, values);
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = uri.getPath().substring(1);
        return dataBase.getWritableDatabase().delete(tableName, selection, selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = uri.getPath().substring(1);
        return dataBase.getWritableDatabase().update(tableName, values, selection, selectionArgs);
    }
}
