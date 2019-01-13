package a1819.m2ihm.sortirametz.bdd.dao.content_provider;

import a1819.m2ihm.sortirametz.bdd.dao.UserDAO;
import a1819.m2ihm.sortirametz.models.User;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContentProviderUserDAO extends ContentProviderDAO implements UserDAO {
    public ContentProviderUserDAO(Context context) {
        super(context);
    }

    @Nullable
    @Override
    public User findByUsername(@NonNull String username) {
        return null;
    }

    @Nullable
    @Override
    public User findByEmail(@NonNull String email) {
        return null;
    }

    @NonNull
    @Override
    public List<User> findAllFriend(@NonNull User user) {
        return null;
    }

    @NotNull
    @Override
    public List<User> findAllOther() {
        return null;
    }

    @NonNull
    @Override
    public List<User> findAll() {
        return null;
    }

    @Nullable
    @Override
    public User find(long id) {
        return null;
    }

    @Override
    public User create(@NonNull User obj) {
        return null;
    }

    @Override
    public void update(@NonNull User obj) {

    }

    @Override
    public void delete(@NonNull User obj) {

    }
}
