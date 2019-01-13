package a1819.m2ihm.sortirametz.bdd;

import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.*;
import a1819.m2ihm.sortirametz.utils.Tuple;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class DataBase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 9;

    private static final String DATABASE_NAME = "VisiteAMetzDB";

    private static final String TABLE_CATEGORIES = "categories";
    private static final String TABLE_PLACES = "places";
    private static final String TABLE_USERS = "users";
    private static final String TABLE_FRIENDS = "friends";

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
    private static final Tuple<String, Integer> KEY_FRIEND_USER1_ID = new Tuple<>("user1_id", 0);
    private static final Tuple<String, Integer> KEY_FRIEND_USER2_ID = new Tuple<>("user2_id", 1);

    private static final String[] FRIENDS_COLUMNS = {KEY_FRIEND_USER1_ID.getV1(), KEY_FRIEND_USER2_ID.getV1()};



    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    /*
     * FONCTION D'AJOUT A LA BASE DE DONNEE
     */

    /**
     * Ajoute une categorie à la base de donnée passé en paramètre
     * @param db Base de donnée à utiliser
     * @param category Categorie à utiliser
     */
    private void addCategory(@NonNull SQLiteDatabase db, @NonNull Category category) {
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Add category :"+category.toString());

        ContentValues values = new ContentValues();
        values.put(KEY_CATEGORY_DESCRIPTION.getV1(), category.getDescription());

        category.setId(db.insert(TABLE_CATEGORIES, null, values));
    }

    /**
     * Ajoute une category à la base de donnée
     * @param category Categorie à ajouter
     * @return La categorie passé en paramètre
     */
    public @NonNull Category addCategory(@NonNull Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        addCategory(db, category);
        db.close();
        return category;
    }

    /**
     * Ajoute un lieu à la base de donnée passé en paramètre
     * @param db Base de donnée à utiliser
     * @param place Lieu à utilisé
     */
    private void addPlace(@NonNull SQLiteDatabase db, @NonNull Place place) {
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

    /**
     * Ajoute un lieu à la base de donnée
     * @param place Lieu à ajouter
     * @return Le lieu passé en paramètre
     */
    public @NonNull Place addPlace(@NonNull Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        addPlace(db, place);
        db.close();
        return place;
    }

    /**
     * Ajoute un utilisateur à la base de donnée passé en paramètre
     * @param db Base de donnée à utiliser
     * @param user Utilisateur à ajouter
     */
    private void addUser(@NonNull SQLiteDatabase db, @NonNull User user) {
        ContentValues values = new ContentValues();
        values.put(KEY_USER_USERNAME.getV1(), user.getUsername());
        values.put(KEY_USER_EMAIL.getV1(), user.getEmail());
        values.put(KEY_USER_PASSWORD.getV1(), user.getPassword());
        values.put(KEY_USER_GENDER.getV1(), user.getGender().getCode());
        values.put(KEY_USER_AVATAR.getV1(), user.getAvatar());

        long id = db.insert(TABLE_USERS, null, values);
        user.setId(id);
    }

    /**
     * Ajoute un utilisateur à la base de donnée
     * @param user Utilisateur à ajouter
     * @return L'utilisateur passé en paramètre
     */
    public @NonNull User addUser(@NonNull User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        addUser(db, user);
        db.close();
        return user;
    }

    public void addFriend(@NonNull SQLiteDatabase db, @NonNull User user1, @NonNull User user2) {
        if (user1.equals(user2)) return;
        if (containsFriend(db, user1, user2)) return;
        ContentValues values = new ContentValues();
        values.put(KEY_FRIEND_USER1_ID.getV1(), user1.getId());
        values.put(KEY_FRIEND_USER2_ID.getV1(), user2.getId());

        db.insert(TABLE_FRIENDS, null, values);
    }

    public void addFriend(@NonNull User user1, @NonNull User user2) {
        SQLiteDatabase db = this.getWritableDatabase();
        addFriend(db, user1, user2);
        db.close();
    }

    /*
     * GETTER DE LA BASE DE DONNEE
     */

    /**
     * Obtient une categorie de la base de donnée en fonction de don id
     * @param id Id de la categorie
     * @return La catégorie trouvée dans le base de donnée, null si aucune trouvé
     */
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

    /**
     * Obtient une catégorie de la base de donnée passé en paramètre en fonction de sa description
     * @param db Base de donnée à utiliser
     * @param description Description de la catégorie
     * @return La catégorie trouvé dans la base de donnée, null si aucune trouvé
     */
    private @Nullable Category getCategory(@NonNull SQLiteDatabase db, @NonNull String description) {
        if (description.equals("")) return null;
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

    /**
     * Obtient un lieu de la base de donnée en fonction de son id
     * @param id Id du lieu à trouver
     * @return Le lieu trouvé dans la base de donnée, null si aucun trouvé
     */
    public @Nullable Place getPlace(long id) {
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

    /**
     * Obtient un utilisateur de la base de donnée en fonction de son email
     * @param email Email de l'utilisateur à chercher
     * @return L'utilisateur trouvé dans la base de donnée, null si aucun trouvé
     */
    public @Nullable User getUserFormEmail(@NonNull String email) {
        if (email.equals("")) return null;
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

    /**
     * Obtient un utilisateur de la base de donnée en fonction de son nom
     * @param username Le nom de l'utilisateur à chercher
     * @return L'utilisateur trouvé dans la base de donnée, null si aucun trouvé
     */
    public @Nullable User getUserFromUsername(@NonNull String username) {
        if (username.equals("")) return null;
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

    /**
     * Obtient un utilisateur de la base de donnée en fonction de son id
     * @param id Id de l'utilisateur à chercher
     * @return L'utilisateur trouvé dans la base de donnée, null si aucun trouvé
     */
    public @Nullable User getUserFromId(long id) {
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

    public @NonNull List<User> getAllFriendByUser(@NonNull User user) {
        List<User> users = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_FRIENDS,
                FRIENDS_COLUMNS,
                KEY_FRIEND_USER1_ID.getV1()+" = ? OR "+ KEY_FRIEND_USER2_ID.getV1()+" = ?",
                new String[] {String.valueOf(user.getId()), String.valueOf(user.getId())},
                null,
                null,
                null,
                null
                );
        if (cursor != null && cursor.moveToFirst()){
            do {
                long id1 = cursor.getLong(0);
                long id2 = cursor.getLong(1);
                User tmp = getUserFromId(id1==user.getId()?id2:id1);
                users.add(tmp);
            }while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return users;
    }

    /**
     * Obtient tout les lieux de la base de donnée
     * @return Une liste qui contient tout les lieux de la base de donnée
     */
    public @NonNull List<Place> getAllPlaces() {
        return getAllPlaces(null);
    }

    /**
     * Obtient toute les catégories de la base ded onnée
     * @return Une liste qui contient toute les catégories de la base de donnée
     */
    public @NonNull List<Category> getAllCategories() {
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

    /**
     * Obtient tout les utilisateurs de la base de donnée
     * @return Une liste qui contient tout les utilisateurs de la base de donnée
     */
    public @NonNull List<User> getAllUsers() {
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

    /**
     * Obtient tout les lieux de la base de données groupé par catégorie
     * La liste contient à la fois les lieux et les catégorie. eg:
     * <ul>
     *     <li><b>Bar</b><ul>
     *         <li>bar1</li>
     *         <li>bar2</li>
     *     </ul></li>
     *     <li><b>Restaurant</b><ul>
     *         <li>Restaurant1</li>
     *         <li>Restaurant2</li>
     *     </ul></li>
     * </ul>
     * @return Une list contenant les catégories ainsi que les lieux associé à chaque catégorie
     */
    public @NonNull List<Recyclerable> getAllPlacesGroupByCategory() {
        List<Recyclerable> recyclerables = new LinkedList<>();
        for (Category category:getAllCategories()) {
            recyclerables.add(category);
            recyclerables.addAll(getAllPlaces(category));
        }
        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]"+recyclerables.toString());
        return recyclerables;
    }


    /**
     * Obtient tout les lieux d'une catégorie
     * @param category Categorie utilisé pour filtrer les lieux, null pour obtenir tous
     * @return Une liste contenant les lieux associé à la catégorie
     */
    private @NonNull List<Place> getAllPlaces(@Nullable Category category) {
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

    public List<User> getAllUserWithout(User user) {
        List<User> users = getAllUsers();
        users.remove(user);
        return users;
    }

    public boolean containsFriend(@NonNull SQLiteDatabase db, @NonNull User user1, @NonNull User user2) {
        Cursor cursor = db.query(
                TABLE_FRIENDS,
                FRIENDS_COLUMNS,
                "("+KEY_FRIEND_USER1_ID.getV1()+" = ? AND "+ KEY_FRIEND_USER2_ID.getV1()+" = ?)" +
                "OR ("+KEY_FRIEND_USER1_ID.getV1()+" = ? AND "+ KEY_FRIEND_USER2_ID.getV1()+" = ?)",
                new String[] {String.valueOf(user1.getId()), String.valueOf(user2.getId()),
                        String.valueOf(user2.getId()), String.valueOf(user1.getId())},
                null,
                null,
                null,
                null
        );
        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    /*
     * LES FONCTIONS DE MISE A JOUR
     */

    /**
     * Met à jour une catégorie
     * @param category Catégorie à mettre à jour
     */
    public void updateCategory(@NonNull Category category) {
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
    }

    /**
     * Met à jour un lieu
     * @param place Lieu à mettre à jour
     */
    public void updatePlace(@NonNull Place place) {
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
    }

    /**
     * Met à jour un utilisateur
     * @param user Utilisateur à mettre à jour
     */
    public void updateUser(@NonNull User user) {
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
    }

    /*
     * FONCTIONS DE SUPPRESSION
     */

    /**
     * Supprime une catégorie
     * @param category Catégorie à supprimer
     */
    public void deleteCategory(@NonNull Category category) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_CATEGORIES,
                KEY_CATEGORY_ID.getV1()+" = ?",
                new String[] {String.valueOf(category.getId())}
        );
        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Delete category :"+category);
    }

    /**
     * Supprime un lieu
     * @param place Lieu à supprimer
     */
    public void deletePlace(@NonNull Place place) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(
                TABLE_PLACES,
                KEY_PLACE_ID.getV1()+" = ?",
                new String[] {String.valueOf(place.getId())}
        );
        db.close();

        Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Delete placeFragment :"+place);
    }

    /*
     * FONCTIONS UTILES
     */

    /**
     * Inser des valeurs dans la base de donnée
     * @param db Base de donnée à utiliser
     */
    private void insertDefaultValues(@NonNull SQLiteDatabase db) {
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

        User dede = new User("dede", "adinquer@yahoo.com", "password", Gender.MAN,"https://i.pinimg.com/originals/71/77/ce/7177ce0c34063053ed5f7218d6ebb458.jpg");
        User goliem = new User("goliem", "golime@sam.io", "password", Gender.MAN);
        User kratheon = new User("kratheon", "kratheon@sam.io", "password", Gender.MAN);
        User bob = new User("bob", "bob@sam.io", "password", Gender.MAN);
        User alice = new User("alice", "alice@sam.io", "password", Gender.WOMAN);

        addUser(db, dede);
        addUser(db, goliem);
        addUser(db, kratheon);
        addUser(db, bob);
        addUser(db, alice);

        addFriend(db, dede, goliem);
        addFriend(db, kratheon, dede);
        addFriend(db, goliem, kratheon);
        addFriend(db, bob, alice);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
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

        String createFriendTable = "CREATE TABLE "+TABLE_FRIENDS+" ("+
                KEY_FRIEND_USER1_ID.getV1()+" INTEGER NOT NULL, "+
                KEY_FRIEND_USER2_ID.getV1()+" INTEGER NOT NULL, " +
                "PRIMARY KEY("+KEY_FRIEND_USER1_ID.getV1()+","+KEY_FRIEND_USER2_ID.getV1()+"))";

        db.execSQL(createCategoryTable);
        db.execSQL(createPlaceTable);
        db.execSQL(createUserTable);
        db.execSQL(createFriendTable);
        insertDefaultValues(db);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FRIENDS);
        this.onCreate(db);
    }


    /**
     * Obtient une catégorie à partir d'un objet cursor
     * @param cursor Curseur sur le résultat d'un SELECT SQL
     * @return La catégorie correspondant, null si le curseur n'est pas associé à une catégorie
     */
    private @Nullable Category cursorToCategory(@Nullable Cursor cursor) {
        Category category = null;

        if (cursor != null && cursor.moveToFirst()){
            category = new Category();

            category.setId(Integer.parseInt(cursor.getString(0)));
            category.setDescription(cursor.getString(1));
            cursor.close();

            Log.d(ValueHelper.INSTANCE.getTag(), "[SQLite]Get category :"+category.toString());
        }else{
            Log.w(ValueHelper.INSTANCE.getTag(), "[SQLite]Impossible to get category ");
        }
        return category;
    }

    /**
     * Obtient un utilisateur à partir d'un objet cursor
     * @param cursor Curseur sur le résultat d'un SELECT SQL
     * @return L'utilisateur correspondant, null si le curseur n'est pas associé à un utilisateur
     */
    private @Nullable User cursorToUser(@Nullable Cursor cursor) {
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
