package a1819.m2ihm.sortirametz.bdd.dao.mariadb;

import a1819.m2ihm.sortirametz.bdd.dao.DAO;
import android.content.Context;

public abstract class MariadbDAO<T> extends DAO<T> {
    public MariadbDAO(Context context) {
        super(context);
        //TODO add connection to db
    }
}
