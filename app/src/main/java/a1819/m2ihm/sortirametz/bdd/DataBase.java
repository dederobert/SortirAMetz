package a1819.m2ihm.sortirametz.bdd;

import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.*;
import a1819.m2ihm.sortirametz.utils.Tuple;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;

    private static final String DATABASE_NAME = "VisiteAMetzDB";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PLACES = "places";
    private static final String TABLE_USERS = "users";

    //TABLE CATEGORY
    private static final Tuple<String, Integer> KEY_CATEGORY_ID = new Tuple<>("id",0);
    private static final Tuple<String, Integer> KEY_CATEGORY_DESCRIPTION = new Tuple<>("description",1);

    private static final String[] CATEGORY_COLUMNS = {KEY_CATEGORY_ID.getV1(), KEY_CATEGORY_DESCRIPTION.getV1()};


    //TABLE PLACES
    private static final Tuple<String, Integer> KEY_PLACE_ID = new Tuple<>("id",0);
    private static final Tuple<String, Integer> KEY_PLACE_NAME = new Tuple<>("name",1);
    private static final Tuple<String, Integer> KEY_PLACE_LATITUDE = new Tuple<>("latitude",2);
    private static final Tuple<String, Integer> KEY_PLACE_LONGITUDE = new Tuple<>("longitude",3);
    private static final Tuple<String, Integer> KEY_PLACE_ADDRESS = new Tuple<>("address",4);
    private static final Tuple<String, Integer> KEY_PLACE_CATEGORY_ID = new Tuple<>("category_id",5);
    private static final Tuple<String, Integer> KEY_PLACE_DESCRIPTION = new Tuple<>("description",6);
    private static final Tuple<String, Integer> KEY_PLACE_ICON = new Tuple<>("icon",7);


    private static final String[] PLACE_COLUMNS = {KEY_PLACE_ID.getV1(), KEY_PLACE_NAME.getV1(),
            KEY_PLACE_LATITUDE.getV1(), KEY_PLACE_LONGITUDE.getV1(), KEY_PLACE_ADDRESS.getV1(),
            KEY_PLACE_CATEGORY_ID.getV1(), KEY_PLACE_DESCRIPTION.getV1(), KEY_PLACE_ICON.getV1()};

    //TABLE USERS
    private static final Tuple<String, Integer> KEY_USER_ID = new Tuple<>("id", 0);
    private static final Tuple<String, Integer> KEY_USER_USERNAME = new Tuple<>("username",1);
    private static final Tuple<String, Integer> KEY_USER_EMAIL = new Tuple<>("email", 2);
    private static final Tuple<String, Integer> KEY_USER_PASSWORD = new Tuple<>("password", 3);
    private static final Tuple<String, Integer> KEY_USER_GENDER = new Tuple<>("gender", 4);
    private static final Tuple<String, Integer> KEY_USER_AVATAR = new Tuple<>("avatar", 5);

    private static final String[] USER_COLUMNS = {KEY_USER_ID.getV1(), KEY_USER_USERNAME.getV1(),
            KEY_USER_EMAIL.getV1(), KEY_USER_PASSWORD.getV1(), KEY_USER_GENDER.getV1(), KEY_USER_AVATAR.getV1()};


    //TABLE FRIENDS
    //private static final Tuple<String, Integer> KEY_FRIEND_USER1_ID = new Tuple("user1_id", 0);
    //private static final Tuple<String, Integer> KEY_FRIEND_USER2_ID = new Tuple("user2_id", 1);

    //private static final String[] FRIENDS_COLUMNS = {KEY_FRIEND_USER1_ID.getV1(), KEY_FRIEND_USER2_ID.getV1()};



    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private void addCategory(SQLiteDatabase db, Category category) {
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Add category :"+category.toString());

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_DESCRIPTION.getV1(), category.getDescription());

        db.insert(TABLE_CATEGORIES, null, values);
    }

    public Category addCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        addCategory(db, category);
        db.close();
        return category;
    }

    private void addPlace(SQLiteDatabase db, Place place) {
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Add placeFragment :"+place.toString());
        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME.getV1(), place.getName());
        values.put(KEY_PLACE_LATITUDE.getV1(), place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE.getV1(), place.getLongitude());
        values.put(KEY_PLACE_ADDRESS.getV1(), place.getAddress());
        values.put(KEY_PLACE_CATEGORY_ID.getV1(), place.getCategory().getId());
        values.put(KEY_PLACE_DESCRIPTION.getV1(), place.getDescription());
        values.put(KEY_PLACE_ICON.getV1(), place.getIcon());

        long id = db.insert(TABLE_PLACES, null, values);
        place.setId(id);
    }

    private void addUser(SQLiteDatabase db, User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME.getV1(), user.getUsername());
        values.put(KEY_USER_EMAIL.getV1(), user.getEmail());
        values.put(KEY_USER_PASSWORD.getV1(), user.getPassword());
        values.put(KEY_USER_GENDER.getV1(), user.getGender().getCode());
        values.put(KEY_USER_AVATAR.getV1(), user.getAvatar());

        long id = db.insert(TABLE_USERS, null, values);
        user.setId(id);
    }

    public User addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        addUser(db, user);
        db.close();
        return user;
    }

    public Place addPlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        addPlace(db, place);
        db.close();
        return place;
    }

    public Category getCategory(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_CATEGORIES,
                CATEGORY_COLUMNS,
                KEY_CATEGORY_ID.getV1()+" = ?",
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
                KEY_CATEGORY_DESCRIPTION.getV1()+" = ?",
                new String[] {description},
                null,
                null,
                null,
                null
        );
        return cursorToCategory(cursor);
    }

    public Place getPlace(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_PLACES,
                PLACE_COLUMNS,
                KEY_PLACE_ID.getV1()+" = ?",
                new String[] {String.valueOf(id)},
                null,
                null,
                null,
                null
        );
        Place place = new Place();

        if (cursor != null){
            cursor.moveToFirst();

            place.setId(Integer.parseInt(cursor.getString(KEY_PLACE_ID.getV2())));
            place.setName(cursor.getString(KEY_PLACE_NAME.getV2()));
            place.setLatitude(Float.parseFloat(cursor.getString(KEY_PLACE_LATITUDE.getV2())));
            place.setLongitude(Float.parseFloat(cursor.getString(KEY_PLACE_LONGITUDE.getV2())));
            place.setAddress(cursor.getString(KEY_PLACE_ADDRESS.getV2()));
            place.setCategory(getCategory(Integer.parseInt(cursor.getString(KEY_PLACE_CATEGORY_ID.getV2()))));
            place.setDescription(cursor.getString(KEY_PLACE_DESCRIPTION.getV2()));
            place.setIcon(cursor.getString(KEY_PLACE_ICON.getV2()));

            cursor.close();

            Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get placeFragment with id "+id+":"+place.toString());
        }else{
            Log.w(ValueHelper.INSTANCE.getTag(), "[SQLite]Impossible to get placeFragment with id "+id);
        }

        db.close();
        return place;
    }

    public User getUserFormEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                USER_COLUMNS,
                KEY_USER_EMAIL.getV1()+" = ?",
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
                KEY_USER_USERNAME.getV1()+" = ?",
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

    public User getUserFromId(long id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_USERS,
                USER_COLUMNS,
                KEY_USER_ID.getV1()+" = ?",
                new String[] {String.valueOf(id)},
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
        return getAllPlaces(null);
    }

    public List<Category> getAllCategories() {
        List<Category> categories = new LinkedList<>();

        String query = "SELECT * FROM "+TABLE_CATEGORIES ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Category category;
        if (cursor.moveToFirst()){
            do {
                category = new Category();
                category.setId(Integer.parseInt(cursor.getString(KEY_CATEGORY_ID.getV2())));
                category.setDescription(cursor.getString(KEY_CATEGORY_DESCRIPTION.getV2()));
                categories.add(category);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get all categories "+categories.toString());

        return categories;
    }

    public List<User> getAllUsers() {
        List<User> users = new LinkedList<>();
        String query = "SELECT * FROM "+TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        User user;
        if (cursor.moveToFirst()) {
            do {
                user = new User();
                user.setId(Integer.parseInt(cursor.getString(KEY_USER_ID.getV2())));
                user.setUsername(cursor.getString(KEY_USER_USERNAME.getV2()));
                user.setEmail(cursor.getString(KEY_USER_EMAIL.getV2()));
                user.setPassword(cursor.getString(KEY_USER_PASSWORD.getV2()));
                user.setGender(Gender.Companion.fromCode(cursor.getString(KEY_USER_GENDER.getV2())));
                user.setAvatar(cursor.getString(KEY_USER_AVATAR.getV2()));
                users.add(user);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get all users"+ users.toString());
        return users;
    }

    public List<Recyclerable> getAllPlacesGroupByCategory() {
        List<Recyclerable> recyclerables = new LinkedList<>();
        for (Category category:getAllCategories()) {
            recyclerables.add(category);
            recyclerables.addAll(getAllPlaces(category));
        }
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]"+recyclerables.toString());
        return recyclerables;
    }


    private List<Place> getAllPlaces(Category category) {
        List<Place> places = new LinkedList<>();
        String query = "SELECT * FROM "+TABLE_PLACES
                + (category!=null?" WHERE "+KEY_PLACE_CATEGORY_ID.getV1()+" = "+category.getId():"")
                + " ORDER BY "+KEY_PLACE_CATEGORY_ID.getV1();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Place place;
        if (cursor.moveToFirst()){
            do {
                place = new Place();
                place.setId(Integer.parseInt(cursor.getString(KEY_PLACE_ID.getV2())));
                place.setName(cursor.getString(KEY_PLACE_NAME.getV2()));
                place.setLatitude(Float.parseFloat(cursor.getString(KEY_PLACE_LATITUDE.getV2())));
                place.setLongitude(Float.parseFloat(cursor.getString(KEY_PLACE_LONGITUDE.getV2())));
                place.setAddress(cursor.getString(KEY_PLACE_ADDRESS.getV2()));
                place.setCategory(getCategory(Integer.parseInt(cursor.getString(KEY_PLACE_CATEGORY_ID.getV2()))));
                place.setDescription(cursor.getString(KEY_PLACE_DESCRIPTION.getV2()));
                place.setIcon(cursor.getString(KEY_PLACE_ICON.getV2()));
                places.add(place);
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get all places "+places.toString());
        return places;
    }

    public Category updateCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_DESCRIPTION.getV1(), category.getDescription());

        db.update(
                TABLE_CATEGORIES,
                values,
                KEY_CATEGORY_ID.getV1()+" = ?",
                new String[] {String.valueOf(category.getId())}
                );

        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Update category :"+category);
        return category;
    }

    public Place updatePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_PLACE_NAME.getV1(), place.getName());
        values.put(KEY_PLACE_LATITUDE.getV1(), place.getLatitude());
        values.put(KEY_PLACE_LONGITUDE.getV1(), place.getLongitude());
        values.put(KEY_PLACE_ADDRESS.getV1(), place.getAddress());
        values.put(KEY_PLACE_CATEGORY_ID.getV1(), place.getCategory().getId());
        values.put(KEY_PLACE_DESCRIPTION.getV1(), place.getDescription());
        values.put(KEY_PLACE_ICON.getV1(), place.getIcon());


        db.update(
                TABLE_PLACES,
                values,
                KEY_PLACE_ID.getV1()+" = ?",
                new String[] {String.valueOf(place.getId())}
        );

        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Update place :"+place);
        return place;
    }

    public User updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME.getV1(), user.getUsername());
        values.put(KEY_USER_EMAIL.getV1(), user.getEmail());
        values.put(KEY_USER_PASSWORD.getV1(), user.getPassword());
        values.put(KEY_USER_GENDER.getV1(), user.getGender().getCode());
        values.put(KEY_USER_AVATAR.getV1(), user.getAvatar());
        
        db.update(
                TABLE_USERS,
                values,
                KEY_USER_ID.getV1()+" = ?",
                new String[] {String.valueOf(user.getId())}
        );
        db.close();
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Update user :"+user);
        return user;
    }

    public void deleteCategory(Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_CATEGORIES,
                KEY_CATEGORY_ID.getV1()+" = ?",
                new String[] {String.valueOf(category.getId())}
        );
        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Delete category :"+category);
    }

    public void deletePlace(Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_PLACES,
                KEY_PLACE_ID.getV1()+" = ?",
                new String[] {String.valueOf(place.getId())}
        );
        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Delete placeFragment :"+place);
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

        addUser(db, new User("dede", "adinquer@yahoo.com", "password", Gender.MAN,"https://i.pinimg.com/originals/71/77/ce/7177ce0c34063053ed5f7218d6ebb458.jpg"));
        addUser(db, new User("goliem", "golime@sam.io", "password", Gender.MAN));
        addUser(db, new User("kratheon", "kratheon@sam.io", "password", Gender.MAN));
        addUser(db, new User("bob", "bob@sam.io", "password", Gender.MAN));
        addUser(db, new User("alice", "alice@sam.io", "password", Gender.WOMAN));
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createCategoryTable = "CREATE TABLE "+TABLE_CATEGORIES+" (" +
                KEY_CATEGORY_ID.getV1() + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_CATEGORY_DESCRIPTION.getV1() + " TINYTEXT)";
        String createPlaceTable = "CREATE TABLE "+TABLE_PLACES+" (" +
                KEY_PLACE_ID.getV1()+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_PLACE_NAME.getV1()+" TINYTEXT," +
                KEY_PLACE_LATITUDE.getV1()+" FLOAT," +
                KEY_PLACE_LONGITUDE.getV1()+" FLOAT," +
                KEY_PLACE_ADDRESS.getV1()+" TINYTEXT," +
                KEY_PLACE_CATEGORY_ID.getV1()+" INTEGER," +
                KEY_PLACE_DESCRIPTION.getV1()+" TEXT," +
                KEY_PLACE_ICON.getV1()+" TEXT," +
                "FOREIGN KEY ("+KEY_PLACE_CATEGORY_ID.getV1()+") REFERENCES "+TABLE_CATEGORIES+"("+KEY_CATEGORY_ID.getV1()+"))";
        String createUserTable = "CREATE TABLE "+TABLE_USERS+" ("+
                KEY_USER_ID.getV1()+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
                KEY_USER_USERNAME.getV1()+" TINYTEXT,"+
                KEY_USER_EMAIL.getV1()+ " TINYTEXT,"+
                KEY_USER_PASSWORD.getV1()+" TEXT," +
                KEY_USER_GENDER.getV1()+ " INT,"+
                KEY_USER_AVATAR.getV1()+ " TEXT)";

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

            Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get category :"+category.toString());
        }else{
            Log.w(ValueHelper.INSTANCE.getTag(), "[SQLite]Impossible to get category ");
        }
        return category;
    }

    private User cursorToUser(Cursor cursor) {
        User user = null;
        if (cursor != null && cursor.moveToFirst()){
            user = new User();
            user.setId(cursor.getLong(KEY_USER_ID.getV2()));
            user.setUsername(cursor.getString(KEY_USER_USERNAME.getV2()));
            user.setEmail(cursor.getString(KEY_USER_EMAIL.getV2()));
            user.setPassword(cursor.getString(KEY_USER_PASSWORD.getV2()));
            user.setGender(Gender.Companion.fromCode(cursor.getString(KEY_USER_GENDER.getV2())));
            user.setAvatar(cursor.getString(KEY_USER_AVATAR.getV2()));

            cursor.close();

        }
        return user;
    }
}
