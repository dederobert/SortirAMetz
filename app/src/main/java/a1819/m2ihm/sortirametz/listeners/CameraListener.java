package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import com.google.android.gms.maps.GoogleMap;

public class CameraListener implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnCameraMoveListener {
    private final MapsActivity activity;

    public CameraListener(MapsActivity mapsActivity) {
        this.activity = mapsActivity;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        this.activity.locator.setCenterOnPosition(true);
        return false;
    }

    @Override
    public void onCameraMove() {
        //this.activity.locator.setCenterOnPosition(false);
    }
}
