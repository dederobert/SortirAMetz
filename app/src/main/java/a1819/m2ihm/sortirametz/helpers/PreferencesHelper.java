package a1819.m2ihm.sortirametz.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public enum PreferencesHelper {
    INSTANCE;

    public enum Unit {
        METER("m"),
        FEET("ft");

        private final float CONVERSION_RATE = 3.2808f;

        private String symbol;

        Unit(String symbol) {
            this.symbol = symbol;
        }

        public float convertToMeter(float value) {
            if (this.equals(METER))
                return value;
            else
                return (value/CONVERSION_RATE);
        }

        public static Unit fromString(final String s) {
            switch (s) {
                case "m":
                    return METER;
                case "ft":
                    return FEET;
                default:
                    return null;
            }
        }

        public String getSymbol() {
            return this.symbol;
        }

    }

    public boolean useFingerprint(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getBoolean("use_fingerprint", false);
    }

    public Unit getUnit(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String unit = sharedPreferences.getString("unit_distance", "m");
        return Unit.fromString(unit);
    }

    public long getGPSInterval(Context activity) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
        String interval = sharedPreferences.getString("sync_frequency", "9000");
        return Long.parseLong(interval);
    }

}
