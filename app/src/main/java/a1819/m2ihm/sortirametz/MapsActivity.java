package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.listeners.LocationButtonListener;
import a1819.m2ihm.sortirametz.models.Place;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private GoogleMap mMap;
    private DataBase dataBase;

    private static final LatLng METZ_LATITUDE_LONGITUDE = new LatLng(49.1244136,6.1790665);
    private static final float DEFAULT_ZOOM = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        dataBase = new DataBase(this);
        //dataBase.addPlace(new Place("Troubadour", 49.1205222f,6.1676358f,
          //      "32 rue du Pont des Morts", dataBase.getCategory(1), "Le troub <3"));
        dataBase.getAllPlaces();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        List<Place> places = dataBase.getAllPlaces();

        for (Place place:places) {
            LatLng coordPlace = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(coordPlace).title(place.getName()));
        }
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition (
                CameraPosition.builder().target(METZ_LATITUDE_LONGITUDE).zoom(DEFAULT_ZOOM).build()
        ));

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)) {
                        Toast.makeText(this, R.string.needed_loaction, Toast.LENGTH_LONG).show();
                    }else{
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
                    }
                } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnMyLocationButtonClickListener(new LocationButtonListener(this));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                //NOT GRANTED
                if (grantResults.length == 1
                        && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    mMap.setMyLocationEnabled(true);
                else Toast.makeText(this, R.string.needed_loaction, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Permet d'ajouter un menu en haut à droite
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_consult:
                goToConsult();
                return true;
            case R.id.menu_map:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToConsult() {
        this.startActivity(new Intent(this, ConsultActivity.class));
        this.finish();
    }
}
