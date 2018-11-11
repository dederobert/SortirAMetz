package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import android.location.Location;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class CustomLocationCallback extends LocationCallback {
    private final MapsActivity activity;

    public CustomLocationCallback(MapsActivity mapsActivity) {
        this.activity = mapsActivity;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        if (locationResult == null){
            return;
        }
        for (Location location: locationResult.getLocations()) {

        }
    }
}
