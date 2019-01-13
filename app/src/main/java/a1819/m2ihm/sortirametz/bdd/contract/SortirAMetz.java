package a1819.m2ihm.sortirametz.bdd.contract;

import android.net.Uri;

public final class SortirAMetz {
    public static final String AUTHORITY = "a1819.m2ihm.sortirametz.provider";
    private static Uri.Builder builder;
    static {
        builder = new Uri.Builder();
        builder.scheme("content");
        builder.authority(AUTHORITY);
    }
    public static final Uri CONTENT_URI = builder.build();

    public static final class Categories {
        private static Uri.Builder builder;
        static {
            builder = SortirAMetz.CONTENT_URI.buildUpon();
            builder.path("categories");
        }
        public static final Uri CONTENT_URI = builder.build();
        public static final String ID = "id";
        public static final String DESCRIPTION = "description";
    }

    public static final class Places {
        private static Uri.Builder builder;
        static {
            builder = SortirAMetz.CONTENT_URI.buildUpon();
            builder.path("places");
        }
        public static final Uri CONTENT_URI = builder.build();
        public static final String ID = "id";
        private static final String NAME = "name";
        private static final String LATITUDE = "latitude";
        private static final String LONGITUDE = "longitude";
        private static final String ADDRESS = "address";
        private static final String CATEGORY_ID = "category_id";
        private static final String DESCRIPTION = "description";
        private static final String ICON = "icon";
    }

    public static final class Users {
        private static Uri.Builder builder;
        static {
            builder = SortirAMetz.CONTENT_URI.buildUpon();
            builder.path("users");
        }
        public static final Uri CONTENT_URI = builder.build();
        public static final String ID = "id";
        private static final String USERNAME = "username";
        private static final String EMAIL = "email";
        private static final String PASSWORD = "password";
        private static final String GENDER = "gender";
        private static final String AVATAR = "avatar";
    }

    public static final class Friends {
        private static Uri.Builder builder;
        static {
            builder = SortirAMetz.CONTENT_URI.buildUpon();
            builder.path("users");
        }
        public static final Uri CONTENT_URI = builder.build();
        private static final String USER1_ID = "user1_id";
        private static final String USER2_ID = "user2_id";
    }
}