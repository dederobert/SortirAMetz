package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Place;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DataBase dataBase;

    private static final LatLng METZ_LATITUDE_LONGIUDE = new LatLng(49.1244136,6.1790665);
    private static final float DEFAULT_ZOOM = 13;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
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
                CameraPosition.builder().target(METZ_LATITUDE_LONGIUDE).zoom(DEFAULT_ZOOM).build()
        ));


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
        //gestion des cliques dans le menu
        return super.onOptionsItemSelected(item);
    }
}
