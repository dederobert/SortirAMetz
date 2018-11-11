package a1819.m2ihm.sortirametz.map;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.MapsActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.listeners.CameraListener;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;
import java.util.List;

import static a1819.m2ihm.sortirametz.MapsActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class Locator extends LocationCallback implements OnMapReadyCallback {

    public class CustomMarker {
        private Category category;
        private Marker marker;

        public CustomMarker(@NonNull Marker marker,@NonNull Category category) {
            this.category = category;
            this.marker = marker;
        }


        public Category getCategory() {
            return category;
        }

        public Marker getMarker() {
            return marker;
        }
    }

    public static final LatLng METZ_LATITUDE_LONGITUDE = new LatLng(49.1244136, 6.1790665);
    private static final float DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    public Circle circle;
    private List<CustomMarker> markers = new LinkedList<>();

    private FusedLocationProviderClient fusedLocationProviderClient;
    public MapFilter mapFilter;
    private MapsActivity activity;
    private LocationRequest locationRequest;
    private boolean centerOnPosition = true;

    public Locator(MapsActivity activity) {
        this.activity =activity;
        this.mapFilter = new MapFilter(this.markers);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity);
        createLocationrequest();
        startLocationUpdates();
    }


    /**
     * Create the location request used to get user location
     */
    protected void createLocationrequest() {

        //Creation d'une request pour la localisation
        locationRequest = new LocationRequest();
        locationRequest.setInterval(PreferencesHelper.INSTANCE.getGPSInterval(this.activity));
        locationRequest.setFastestInterval(4000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        //verification des settings
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(this.activity);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnFailureListener(this.activity, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                int statusCode = ((ApiException) e).getStatusCode();
                switch (statusCode) {
                    case CommonStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // Then check the result in onActivityResult().
                            ResolvableApiException resolvable = (ResolvableApiException) e;
                            resolvable.startResolutionForResult(activity, 0x1);
                        } catch (IntentSender.SendIntentException ignored) {}
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we will not show the dialog.
                        break;
                }
            }
        });
    }

    @Override
    public void onLocationResult(LocationResult location) {
        //On obtient la possition de l'utilisateur
        if (location == null)
            return;

        mapFilter.setCurrentLocation(location.getLastLocation());
        mapFilter.filterMarker();
        //On déplace le cercle sur sa position
        circle.setCenter(new LatLng(location.getLastLocation().getLatitude(),
                location.getLastLocation().getLongitude()));
        //On déplace la caméra sur sa position, si l'app est centré sur sa position
        if (this.centerOnPosition)
            getMap().moveCamera(CameraUpdateFactory.newCameraPosition(
                    CameraPosition.builder().target(
                            new LatLng(location.getLastLocation().getLatitude(), location.getLastLocation().getLongitude())
                    ).zoom(getMap().getCameraPosition().zoom).build()));
    }

    /**
     * Start the location request
     * it call when app resume
     */
    public void startLocationUpdates() {
        //Verification des permissions
        if (ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.activity, a1819.m2ihm.sortirametz.R.string.needed_loaction, Toast.LENGTH_LONG).show();
            return;
        }
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(PreferencesHelper.INSTANCE.getGPSInterval(this.activity));
        fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this, null);

    }

    /**
     * Stop the location request
     * it call when app pause
     */
    public void stopLocationUpdates() {
        locationRequest.setPriority(LocationRequest.PRIORITY_NO_POWER);
        locationRequest.setInterval(20000);
        fusedLocationProviderClient.removeLocationUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Lorsque la carte est chargée
        mMap = googleMap;

        //On place tout les points sur la carte
        List<Place> places = this.activity.dataBase.getAllPlaces();
        for (Place place : places) {
            LatLng coordPlace = new LatLng(place.getLatitude(), place.getLongitude());
            markers.add(new CustomMarker(
                    mMap.addMarker(new MarkerOptions().position(coordPlace).title(place.getName())
                    ), place.getCategory()));
        }

        //On vérfiie les permissions, les demande à l'utilisateur et affiche le bouton pour recentré la carte
        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this.activity, a1819.m2ihm.sortirametz.R.string.needed_loaction, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this.activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(METZ_LATITUDE_LONGITUDE).zoom(DEFAULT_ZOOM).build()
        ));

        //On ajoute un listener du bouton pour centrer sur la position
        CameraListener listener = new CameraListener(this.activity);
        mMap.setOnMyLocationButtonClickListener(listener);
        mMap.setOnCameraMoveListener(listener);

        //On dessine un cercle
        circle = mMap.addCircle(new CircleOptions()
                .center(METZ_LATITUDE_LONGITUDE)
                .radius(200)
                .strokeColor(a1819.m2ihm.sortirametz.R.color.colorPrimary)
                .fillColor(R.color.colorFillCircle)
        );
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void setCenterOnPosition(boolean centerOnPosition) {
        this.centerOnPosition = centerOnPosition;
        Log.d(ConsultActivity.APP_TAG, "[LOCATOR] set center : "+this.centerOnPosition);
    }

}
