package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface UserDAO extends DAO<User> {
    @Nullable User findByUsername(String username);
    @Nullable User findByEmail(String email);
}
