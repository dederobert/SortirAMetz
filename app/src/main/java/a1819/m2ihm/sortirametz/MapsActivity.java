package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.listeners.FilterButtonListener;
import a1819.m2ihm.sortirametz.map.Locator;
import a1819.m2ihm.sortirametz.models.Category;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.MapFragment;

import java.util.List;

public class MapsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_LOGIN = 14;
    public Locator locator;

    public DataBase dataBase;

    @BindView(R.id.edt_filter_radius) EditText edt_filter_radius;
    @BindView(R.id.spi_filter_category) Spinner spi_filter_category;
    @BindView(R.id.btn_filter) Button btn_filter;
    public Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        startActivityForResult(new Intent(this, LoginActivity.class), REQUEST_LOGIN);

        dataBase = new DataBase(this);
        locator = new Locator(this);

        ButterKnife.bind(this);
        //edt_filter_radius = findViewById(R.id.edt_filter_radius);
        //spi_filter_category = findViewById(R.id.spi_filter_category);
        //btn_filter = findViewById(R.id.btn_filter);

        //Change the symbol of radius according to preferences
        edt_filter_radius.setHint(edt_filter_radius.getHint()
                +" ("+ PreferencesHelper.INSTANCE.getUnit(this).getSymbol()+")");
        btn_filter.setOnClickListener(new FilterButtonListener(this));

        //Set spinner content
        List<Category> categories = dataBase.getAllCategories();
        categories.add(0, new Category(getResources().getString(R.string.all), true));
        spi_filter_category.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_filter_category.setAdapter(adapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this.locator);
    }

    @Override
    protected void onResume() {
        super.onResume();
        locator.startLocationUpdates();
    }

    @Override
    protected void onPause() {
        super.onPause();
        locator.stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                //NOT GRANTED
                if (grantResults.length == 1
                        && permissions[0].equals(Manifest.permission.ACCESS_FINE_LOCATION)
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    locator.getMap().setMyLocationEnabled(true);
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
            case R.id.menu_setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToConsult() {
        this.startActivity(new Intent(this, ConsultActivity.class));
        this.finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedCategory = (Category)parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.selectedCategory = null;
    }

    public String getRadius() {
        return edt_filter_radius.getText().toString();
    }
}
