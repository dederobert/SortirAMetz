package a1819.m2ihm.sortirametz.bdd.dao;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public interface DAO<T> {

    List<T> findAll();
    @Nullable T find(long id);
    T create(@NonNull T obj);
    void update(@NonNull T obj);
    void delete(@NonNull T obj);
}
