package a1819.m2ihm.sortirametz.utils;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Gender;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.User;
import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import static a1819.m2ihm.sortirametz.bdd.DataBase.*;

public enum  Bdd {
    INSTANCE;

    /**
     * Obtient une catégorie à partir d'un objet cursor
     * @param cursor Curseur sur le résultat d'un SELECT SQL
     * @return La catégorie correspondant, null si le curseur n'est pas associé à une catégorie
     */
    public @Nullable
    Category cursorToCategory(@Nullable Cursor cursor) {
        Category category = null;

        if (cursor != null && cursor.moveToFirst()){
            category = new Category();

            category.setId(Integer.parseInt(cursor.getString(DataBase.KEY_PLACE_CATEGORY_ID.getV2())));
            category.setDescription(cursor.getString(DataBase.KEY_PLACE_DESCRIPTION.getV2()));

            Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get category :"+category.toString());
        }else{
            Log.w(ValueHelper.INSTANCE.getTag(), "[SQLite]Impossible to get category ");
        }
        return category;
    }

    public @NonNull
    List<Category> cursorToCategories(@Nullable Cursor cursor){
        List<Category> categories = new LinkedList<>();
        if (cursor!=null && cursor.moveToFirst()){
            do {
                categories.add(cursorToCategory(cursor));
            }while (cursor.moveToNext());
        }
        return categories;
    }

    /**
     * Obtient un utilisateur à partir d'un objet cursor
     * @param cursor Curseur sur le résultat d'un SELECT SQL
     * @return L'utilisateur correspondant, null si le curseur n'est pas associé à un utilisateur
     */
    public  @Nullable
    User cursorToUser(@Nullable Cursor cursor) {
        User user = null;
        if (cursor != null && cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(DataBase.KEY_USER_ID.getV2()));
            user.setUsername(cursor.getString(DataBase.KEY_USER_USERNAME.getV2()));
            user.setEmail(cursor.getString(DataBase.KEY_USER_EMAIL.getV2()));
            user.setPassword(cursor.getString(DataBase.KEY_USER_PASSWORD.getV2()));
            user.setGender(Gender.Companion.fromCode(cursor.getString(DataBase.KEY_USER_GENDER.getV2())));
            user.setAvatar(cursor.getString(DataBase.KEY_USER_AVATAR.getV2()));
        }
        return user;
    }

    public @NonNull
    ContentValues placeToContentValues(@NonNull Place place) {
        ContentValues values = new ContentValues();
        if (place.getId() != -1) values.put(KEY_PLACE_ID.getV1(), place.getId());
        values.put(KEY_PLACE_NAME.getV1(), place.getName());
        values.put(KEY_PLACE_LATITUDE.getV1(), place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE.getV1(), place.getLongitude());
        values.put(KEY_PLACE_ADDRESS.getV1(), place.getAddress());
        values.put(KEY_PLACE_CATEGORY_ID.getV1(), place.getCategory().getId());
        values.put(KEY_PLACE_DESCRIPTION.getV1(), place.getDescription());
        values.put(KEY_PLACE_ICON.getV1(), place.getIcon());
        return values;
    }

    public @NonNull
    ContentValues userToContentValues(@NonNull User user) {
        ContentValues values = new ContentValues();
        if (user.getId() != -1) values.put(KEY_USER_ID.getV1(), user.getId());
        values.put(KEY_USER_USERNAME.getV1(), user.getUsername());
        values.put(KEY_USER_EMAIL.getV1(), user.getEmail());
        values.put(KEY_USER_PASSWORD.getV1(), user.getPassword());
        values.put(KEY_USER_GENDER.getV1(), user.getGender().getCode());
        values.put(KEY_USER_AVATAR.getV1(), user.getAvatar());
        return values;
    }

    public @NonNull
    ContentValues categoryToContentValues(@NonNull Category category) {
        ContentValues values = new ContentValues();
        if (category.getId() != -1) values.put(KEY_CATEGORY_ID.getV1(), category.getId());
        values.put(KEY_CATEGORY_DESCRIPTION.getV1(), category.getDescription());
        return values;
    }


}
