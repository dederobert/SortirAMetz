package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.listeners.LocationButtonListener;
import a1819.m2ihm.sortirametz.models.Place;
import android.Manifest;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.*;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.*;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.LinkedList;
import java.util.List;

import static a1819.m2ihm.sortirametz.MapsActivity.MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class Locator extends LocationCallback implements OnMapReadyCallback {

    public static final LatLng METZ_LATITUDE_LONGITUDE = new LatLng(49.1244136, 6.1790665);
    private static final float DEFAULT_ZOOM = 15;

    private GoogleMap mMap;
    public Circle circle;
    private List<Marker> markers = new LinkedList<>();


    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location currentLocation;
    private MapsActivity activity;
    private LocationRequest locationRequest;

    public Locator(MapsActivity activity) {
        this.activity =activity;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.activity);
        createLocationrequest();
        startLocationUpdates();
    }


    protected void createLocationrequest() {
        //Creation d'une request pour la localisation

        locationRequest = new LocationRequest();
        locationRequest.setInterval(9000);
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
                        } catch (IntentSender.SendIntentException sendEx) {
                        }
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
        if (location == null)
            return;
        Log.d("LOCATOR", location.getLastLocation().toString());
        circle.setCenter(new LatLng(location.getLastLocation().getLatitude(),
                location.getLastLocation().getLongitude()));
        getMap().moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(
                        new LatLng(location.getLastLocation().getLatitude(), location.getLastLocation().getLongitude())
                ).zoom(getMap().getCameraPosition().zoom).build()));
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this.activity, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(this.activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this.activity, R.string.needed_loaction, Toast.LENGTH_LONG).show();
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(this.locationRequest, this, null);
    }

    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        List<Place> places = this.activity.dataBase.getAllPlaces();

        for (Place place : places) {
            LatLng coordPlace = new LatLng(place.getLatitude(), place.getLongitude());
            markers.add(mMap.addMarker(new MarkerOptions().position(coordPlace).title(place.getName())));
        }


        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                CameraPosition.builder().target(METZ_LATITUDE_LONGITUDE).zoom(DEFAULT_ZOOM).build()
        ));

        if (ContextCompat.checkSelfPermission(this.activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this.activity, R.string.needed_loaction, Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this.activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMyLocationButtonClickListener(new LocationButtonListener(this.activity));

        circle = mMap.addCircle(new CircleOptions()
                .center(METZ_LATITUDE_LONGITUDE)
                .radius(200)
                .strokeColor(R.color.colorPrimary)
                .fillColor(R.color.colorFillCircle)
        );
    }

    public GoogleMap getMap() {
        return mMap;
    }
}
