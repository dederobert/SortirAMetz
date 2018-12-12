package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import android.content.Context;

abstract class SQLiteDAO {

    DataBase dataBase;

    SQLiteDAO(Context context) {
        this.dataBase = new DataBase(context);
    }
}