package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.listeners.FilterButtonListener;
import a1819.m2ihm.sortirametz.map.Locator;
import a1819.m2ihm.sortirametz.models.Category;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.gms.maps.MapFragment;

import java.util.List;
import java.util.Objects;

public class MapsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    public Locator locator;
    private FilterButtonListener filterListener;
    @BindView(R.id.edt_filter_radius) EditText edt_filter_radius;
    @BindView(R.id.spi_filter_category) Spinner spi_filter_category;
    public Category selectedCategory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        locator = new Locator(getActivity());

        CategoryDAO categoryDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), ConsultFragment.FACTORY_TYPE)).getCategoryDAO();

        //Change the symbol of radius according to preferences
        edt_filter_radius.setHint(edt_filter_radius.getHint()
                +" ("+ PreferencesHelper.INSTANCE.getUnit(getContext()).getSymbol()+")");
        edt_filter_radius.setImeActionLabel(this.getResources().getString(R.string.filter), KeyEvent.KEYCODE_ENTER);
        filterListener = new FilterButtonListener(this, getActivity());

        edt_filter_radius.setOnEditorActionListener(filterListener);

        //Set spinner content
        List<Category> categories = categoryDAO.findAll();//dataBase.getAllCategories();
        categories.add(0, new Category(getResources().getString(R.string.all), true));
        spi_filter_category.setOnItemSelectedListener(this);
        ArrayAdapter adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_filter_category.setAdapter(adapter);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        MapFragment mapFragment = (MapFragment) getActivity().getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this.locator);
    }

    @Override
    public void onResume() {
        super.onResume();
        locator.startLocationUpdates();
    }

    @Override
    public void onPause() {
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
                else Toast.makeText(getContext(), R.string.needed_loaction, Toast.LENGTH_LONG).show();
            }
        }
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Permet d'ajouter un menu en haut à droite
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_consult:
                goToConsult();
                return true;
            case R.id.menu_map:
                return true;
            case R.id.menu_setting:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.menu_disconnect:
                Logger.INSTANCE.disconnect(this);
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToConsult() {
        this.startActivity(new Intent(this, ConsultFragment.class));
        this.finish();
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.selectedCategory = (Category)parent.getItemAtPosition(position);
        this.filterListener.filter();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.selectedCategory = null;
    }

    public String getRadius() {
        return edt_filter_radius.getText().toString();
    }
}
