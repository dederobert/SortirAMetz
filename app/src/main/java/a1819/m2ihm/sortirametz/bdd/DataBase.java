package a1819.m2ihm.sortirametz.bdd;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "VisiteAMetzDB";


    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PLACES = "places";

    private static final String KEY_CATEGORY_ID = "id";
    private static final String KEY_CATEGORY_DESCRIPTION = "description";

    private static final String[] CATEGORY_COLUMNS = {KEY_CATEGORY_ID, KEY_CATEGORY_DESCRIPTION};

    private static final String KEY_PLACE_ID = "id";
    private static final String KEY_PLACE_NAME = "name";
    private static final String KEY_PLACE_LATITUDE = "latitude";
    private static final String KEY_PLACE_LONGITUDE = "longitude";
    private static final String KEY_PLACE_ADDRESS = "address";
    private static final String KEY_PLACE_CATEGORY_ID = "category_id";
    private static final String KEY_PLACE_DESCRIPTION = "description";
    private static final String KEY_PLACE_ICON = "icon";


    private static final String[] PLACE_COLUMNS = {KEY_PLACE_ID, KEY_PLACE_NAME, KEY_PLACE_LATITUDE,
            KEY_PLACE_LONGITUDE, KEY_PLACE_ADDRESS, KEY_PLACE_CATEGORY_ID, KEY_PLACE_DESCRIPTION, KEY_PLACE_ICON};

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void addCategory(SQLiteDatabase db, Category category) {
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Add category :"+category.toString());

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_DESCRIPTION, category.getDescription());

        db.insert(TABLE_CATEGORIES, null, values);
    }

    public void addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        addCategory(db, category);
        db.close();
    }

    private void addPlace(SQLiteDatabase db, Place place) {
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Add placeFragment :"+place.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getName());
        values.put(KEY_PLACE_LATITUDE, place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE, place.getLongitude());
        values.put(KEY_PLACE_ADDRESS, place.getAddress());
        values.put(KEY_PLACE_CATEGORY_ID, place.getCategory().getId());
        values.put(KEY_PLACE_DESCRIPTION, place.getDescription());
        values.put(KEY_PLACE_ICON, place.getIcon());

        db.insert(TABLE_PLACES, null, values);
    }

    public void addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        addPlace(db, place);
        db.close();
    }

    public Category getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CATEGORIES,
                CATEGORY_COLUMNS,
                KEY_CATEGORY_ID+" = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null
                );
        Category category = cursorToCategory(cursor);
        db.close();
        return category;
    }

    private Category getCategory(SQLiteDatabase db, String description) {
        Cursor cursor = db.query(
                TABLE_CATEGORIES,
                CATEGORY_COLUMNS,
                KEY_CATEGORY_DESCRIPTION+" = ?",
                new String[] {description},
                null,
                null,
                null,
                null
        );
        return cursorToCategory(cursor);
    }

    public Category getCategory(String description) {
        SQLiteDatabase db = this.getReadableDatabase();
        Category category = getCategory(db, description);
        db.close();
        return category;
    }

    public Place getPlace(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PLACES,
                PLACE_COLUMNS,
                KEY_PLACE_ID+" = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        Place place = new Place();

        if (cursor != null){
            cursor.moveToFirst();

            place.setId(Integer.parseInt(cursor.getString(0)));
            place.setName(cursor.getString(1));
            place.setLatitude(Float.parseFloat(cursor.getString(2)));
            place.setLongitude(Float.parseFloat(cursor.getString(3)));
            place.setAddress(cursor.getString(4));
            place.setCategory(getCategory(Integer.parseInt(cursor.getString(5))));
            place.setDescription(cursor.getString(6));
            place.setIcon(cursor.getString(7));

            cursor.close();

            Log.d(ConsultActivity.APP_TAG, "[SQLite]Get placeFragment with id "+id+":"+place.toString());
        }else{
            Log.w(ConsultActivity.APP_TAG, "[SQLite]Impossible to get placeFragment with id "+id);
        }

        db.close();
        return place;
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<>();

        String query = "SELECT * FROM "+TABLE_CATEGORIES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Category category = null;
        if (cursor.moveToFirst()){
            do {
                category = new Category();
                category.setId(Integer.parseInt(cursor.getString(0)));
                category.setDescription(cursor.getString(1));
                categories.add(category);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Get all categories "+categories.toString());

        return categories;
    }

    /*public List<String> getAllCategoriesName() {
        List<String> names = new LinkedList<>();
        String query = "SELECT "+KEY_CATEGORY_DESCRIPTION+" FROM "+TABLE_CATEGORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String name = null;
        if (cursor.moveToFirst()) {
            do {
                name = cursor.getString(0);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Get all categories name "+names.toString());
        return names;
    }*/

    public List<Place> getAllPlaces() {
        List<Place> places = new LinkedList<>();

        String query = "SELECT * FROM "+TABLE_PLACES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Place place = null;
        if (cursor.moveToFirst()){
            do {
                place = new Place();
                place.setId(Integer.parseInt(cursor.getString(0)));
                place.setName(cursor.getString(1));
                place.setLatitude(Float.parseFloat(cursor.getString(2)));
                place.setLongitude(Float.parseFloat(cursor.getString(3)));
                place.setAddress(cursor.getString(4));
                place.setCategory(getCategory(Integer.parseInt(cursor.getString(5))));
                place.setDescription(cursor.getString(6));
                place.setIcon(cursor.getString(7));
                places.add(place);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Get all places "+places.toString());
        return places;
    }

    public int updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_DESCRIPTION, category.getDescription());

        int i = db.update(
                TABLE_CATEGORIES,
                values,
                KEY_CATEGORY_ID+" = ?",
                new String[] {String.valueOf(category.getId())}
                );

        db.close();

        Log.d(ConsultActivity.APP_TAG, "[SQLite]Update category :"+category);
        return i;
    }

    public int updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME, place.getName());
        values.put(KEY_PLACE_LATITUDE, place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE, place.getLongitude());
        values.put(KEY_PLACE_ADDRESS, place.getAddress());
        values.put(KEY_PLACE_CATEGORY_ID, place.getCategory().getId());
        values.put(KEY_PLACE_DESCRIPTION, place.getDescription());
        values.put(KEY_PLACE_ICON, place.getIcon());


        int i = db.update(
                TABLE_PLACES,
                values,
                KEY_PLACE_ID+" = ?",
                new String[] {String.valueOf(place.getId())}
        );

        db.close();

        Log.d(ConsultActivity.APP_TAG, "[SQLite]Update placeFragment :"+place);
        return i;
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_CATEGORIES,
                KEY_CATEGORY_ID+" = ?",
                new String[] {String.valueOf(category.getId())}
        );
        db.close();

        Log.d(ConsultActivity.APP_TAG, "[SQLite]Delete category :"+category);
    }

    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_PLACES,
                KEY_PLACE_ID+" = ?",
                new String[] {String.valueOf(place.getId())}
        );
        db.close();

        Log.d(ConsultActivity.APP_TAG, "[SQLite]Delete placeFragment :"+place);
    }

    private void insertDefaultValues(SQLiteDatabase db) {
        addCategory(db ,new Category("bar"));
        addCategory(db, new Category("restaurant"));
        addCategory(db, new Category("fast-food"));

        addPlace(db, new Place("Troubadour", 49.1205222f,6.1676358f,
                      "32 rue du Pont des Morts", getCategory(db,"bar"), "Le troub <3",
                "http://metz.curieux.net/agenda/images/lieux/_31-logo.png"));
        addPlace(db, new Place("Boogie Burger", 49.1198784f,6.1680732f,
                "1 rue du Pont des Morts", getCategory(db,"restaurant"),
                "Hamburger viande & pain maison",
                "https://media-cdn.tripadvisor.com/media/photo-s/08/84/8d/e2/burger-boogie.jpg"));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoryTable = "CREATE TABLE "+TABLE_CATEGORIES+" (" +
                KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_CATEGORY_DESCRIPTION + " TINYTEXT)";
        String createPlaceTable = "CREATE TABLE "+TABLE_PLACES+" (" +
                KEY_PLACE_ID+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PLACE_NAME+" TINYTEXT," +
                KEY_PLACE_LATITUDE+" FLOAT," +
                KEY_PLACE_LONGITUDE+" FLOAT," +
                KEY_PLACE_ADDRESS+" TINYTEXT," +
                KEY_PLACE_CATEGORY_ID+" INTEGER," +
                KEY_PLACE_DESCRIPTION+" TEXT," +
                KEY_PLACE_ICON+" TEXT," +
                "FOREIGN KEY ("+KEY_PLACE_CATEGORY_ID+") REFERENCES "+TABLE_CATEGORIES+"("+KEY_CATEGORY_ID+"))";

        db.execSQL(createCategoryTable);
        db.execSQL(createPlaceTable);
        insertDefaultValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        this.onCreate(db);
    }

    private Category cursorToCategory(Cursor cursor) {
        Category category = new Category();

        if (cursor != null){
            cursor.moveToFirst();

            category.setId(Integer.parseInt(cursor.getString(0)));
            category.setDescription(cursor.getString(1));
            cursor.close();

            Log.d(ConsultActivity.APP_TAG, "[SQLite]Get category :"+category.toString());
        }else{
            Log.w(ConsultActivity.APP_TAG, "[SQLite]Impossible to get category ");
        }
        return category;
    }
}
