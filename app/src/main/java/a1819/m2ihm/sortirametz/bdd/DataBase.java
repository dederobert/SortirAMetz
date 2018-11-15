package a1819.m2ihm.sortirametz.bdd;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import a1819.m2ihm.sortirametz.models.User;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "VisiteAMetzDB";


    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PLACES = "places";
    private static final String TABLE_USERS = "users";

    //TABLE CATEGORY
    private static final String KEY_CATEGORY_ID = "id";
    private static final String KEY_CATEGORY_DESCRIPTION = "description";

    private static final String[] CATEGORY_COLUMNS = {KEY_CATEGORY_ID, KEY_CATEGORY_DESCRIPTION};


    //TABLE PLACES
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

    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_EMAIL = "email";
    private static final String KEY_USER_PASSWORD = "password";

    private static final String[] USER_COLUMNS = {KEY_USER_ID, KEY_USER_USERNAME, KEY_USER_EMAIL, KEY_USER_PASSWORD};


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

        long id = db.insert(TABLE_PLACES, null, values);
        place.setId(id);
    }

    public User addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PASSWORD, user.getPassword());

        long id = db.insert(TABLE_USERS, null, values);
        user.setId(id);
        return user;
    }

    public Place addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        addPlace(db, place);
        db.close();
        return place;
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

    public Place getPlace(long id) {
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

    public User getUserFormEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                USER_COLUMNS,
                KEY_USER_EMAIL+" = ?",
                new String[] {email},
                null,
                null,
                null,
                null
        );
        User user = cursorToUser(cursor);
        db.close();
        return user;
    }

    public User getUserFromUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                USER_COLUMNS,
                KEY_USER_USERNAME+" = ?",
                new String[] {username},
                null,
                null,
                null,
                null
        );
        User user = cursorToUser(cursor);
        db.close();
        return user;
    }

    public List<Place> getAllPlaces() {
        return getAllPlaces(null,false);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<>();

        String query = "SELECT * FROM "+TABLE_CATEGORIES ;

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

    public List<Recyclerable> getAllPlacesGroupByCategory() {
        List<Recyclerable> recyclerables = new LinkedList<>();
        for (Category category:getAllCategories()) {
            recyclerables.add(category);
            recyclerables.addAll(getAllPlaces(category));
        }
        Log.d(ConsultActivity.APP_TAG, "[SQLite]"+recyclerables.toString());
        return recyclerables;
    }

    public List<Place> getAllPlaces(Category category) {
        return getAllPlaces(category, false);
    }

    public List<Place> getAllPlaces(boolean sortByCategory) {
        return getAllPlaces(null, sortByCategory);
    }

    public List<Place> getAllPlaces(Category category, boolean sortByCategory) {
        List<Place> places = new LinkedList<>();
        String query = "SELECT * FROM "+TABLE_PLACES
                + (category!=null?" WHERE "+KEY_PLACE_CATEGORY_ID+" = "+category.getId():"")
                + (sortByCategory?" ORDER BY "+KEY_PLACE_CATEGORY_ID:"");

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

    public Place updatePlace(Place place) {
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

        Log.d(ConsultActivity.APP_TAG, "[SQLite]Update place :"+place);
        return place;
    }

    public User updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME, user.getUsername());
        values.put(KEY_USER_EMAIL, user.getEmail());
        values.put(KEY_USER_PASSWORD, user.getPassword());
        
        int i = db.update(
                TABLE_USERS,
                values,
                KEY_USER_ID+" = ?",
                new String[] {String.valueOf(user.getId())}
        );
        db.close();
        Log.d(ConsultActivity.APP_TAG, "[SQLite]Update user :"+user);
        return user;
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

        addPlace(db, new Place("Le Troubadour", 49.1205629f,6.169062000000001f,
                      "32 rue du Pont des Morts, 57000 Metz, France", getCategory(db,"bar"),
                "Le troub <3","http://metz.curieux.net/agenda/images/lieux/_31-logo.png"));
        addPlace(db, new Place("Boogie Burger", 49.1198749f,6.1702619f,
                "1 rue du Pont des Morts, 5700 Metz, France", getCategory(db,"fast-food"),
                "Hamburger viande & pain maison","https://media-cdn.tripadvisor.com/media/photo-s/08/84/8d/e2/burger-boogie.jpg"));
        addPlace(db, new Place("Comédie Café", 49.1203810999999996f, 6.173010899999995f,
                "2 Rue du Pont des Roches, 57000 Metz, France", getCategory(db, "bar"),
                "", "http://www.ascmoulinslesmetz.com/wp-content/uploads/2013/03/comedie-cafe1.png"));
        addPlace(db, new Place("City wok", 49.116267000000001f, 6.177634f,
                "40 Rue de la Chèvre, 57000 Metz, France", getCategory(db ,"restaurant"),
                "", "https://img.over-blog-kiwi.com/1020x765/1/26/14/30/20141019/ob_82fa48_sushi-city-wok.jpg"));
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
        String createUserTable = "CREATE TABLE "+TABLE_USERS+" ("+
                KEY_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_USER_USERNAME+" TINYTEXT,"+
                KEY_USER_EMAIL+ " TINYTEXT,"+
                KEY_USER_PASSWORD+" TEXT)";

        db.execSQL(createCategoryTable);
        db.execSQL(createPlaceTable);
        db.execSQL(createUserTable);
        insertDefaultValues(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
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

    private User cursorToUser(Cursor cursor) {
        User user = null;
        if (cursor != null && cursor.moveToFirst()){
            user = new User();

            user.setId(cursor.getLong(0));
            user.setUsername(cursor.getString(1));
            user.setEmail(cursor.getString(2));
            user.setPassword(cursor.getString(3));

            cursor.close();

        }
        return user;
    }

    public User getUserFromId(long id) {
    }
}
