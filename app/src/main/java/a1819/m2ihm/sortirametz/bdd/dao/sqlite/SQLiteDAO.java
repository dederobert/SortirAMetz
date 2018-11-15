package a1819.m2ihm.sortirametz.bdd.dao.sqlite;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import android.content.Context;

public abstract class SQLiteDAO<T> extends DAO<T> {
    DataBase dataBase;

    public SQLiteDAO(Context context) {
        super(context);
        dataBase = new DataBase(context);
    }
}
