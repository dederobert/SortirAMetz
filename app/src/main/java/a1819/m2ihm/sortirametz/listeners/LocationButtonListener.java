package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import com.google.android.gms.maps.GoogleMap;

public class LocationButtonListener implements GoogleMap.OnMyLocationButtonClickListener {
    public LocationButtonListener(MapsActivity mapsActivity) {
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }
}
