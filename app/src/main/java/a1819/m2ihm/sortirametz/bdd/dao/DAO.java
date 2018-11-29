package a1819.m2ihm.sortirametz.bdd.dao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface DAO<T> {

    public List<T> findAll();
    public @Nullable T find(long id);
    public T create(@NonNull T obj);
    public T update(@NonNull T obj);
    public void delete(@NonNull T obj);
}
