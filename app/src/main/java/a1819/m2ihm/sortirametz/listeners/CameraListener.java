package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.map.Locator;
import com.google.android.gms.maps.GoogleMap;

public class CameraListener implements GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnCameraMoveListener {
    private final Locator locator;

    public CameraListener(Locator locator) {
        this.locator = locator;
    }

    @Override
    public boolean onMyLocationButtonClick() {
        this.locator.setCenterOnPosition(true);
        return false;
    }

    @Override
    public void onCameraMove() {
        //this.activity.locator.setCenterOnPosition(false);
    }
}
