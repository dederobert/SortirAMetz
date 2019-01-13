package a1819.m2ihm.sortirametz.bdd.dao;

import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface UserDAO extends DAO<User> {
    @Nullable User findByUsername(@NonNull String username);
    @Nullable User findByEmail(@NonNull String email);
    @NonNull List<User> findAllFriend(@NonNull User user);

    @NotNull List<User> findAllOther();
}
