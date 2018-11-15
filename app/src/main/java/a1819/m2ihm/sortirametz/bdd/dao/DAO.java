package a1819.m2ihm.sortirametz.bdd.dao;

import android.content.Context;

public abstract class DAO<T> {

    protected Context context;
    public DAO(Context context) {
        this.context = context;
    }

    public abstract T find(long id);
    public abstract T create(T obj);
    public abstract T update(T obj);
    public abstract void delete(T obj);
}
