package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.listeners.FilterButtonListener;
import a1819.m2ihm.sortirametz.map.Locator;
import a1819.m2ihm.sortirametz.models.Category;
import android.Manifest;
import android.annotation.SuppressLint;
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

    private View rootView;
    private MapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView!= null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent!= null) parent.removeView(rootView);
        }

        try {
            rootView = inflater.inflate(R.layout.fragment_maps, container, false);
            ButterKnife.bind(this, rootView);
        }catch (InflateException ignored) {}
        edt_filter_radius.setHint(edt_filter_radius.getHint()
                +" ("+ PreferencesHelper.INSTANCE.getUnit(getContext()).getSymbol()+")");
        edt_filter_radius.setImeActionLabel(this.getResources().getString(R.string.filter), KeyEvent.KEYCODE_ENTER);
        setupMap();
        return rootView;
    }

    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        if(mapFragment==null) {
            mapFragment = (MapFragment) getActivity().getFragmentManager()
                    .findFragmentById(R.id.map);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //Change the symbol of radius according to preferences



    }

    @Override
    public void onStart() {
        super.onStart();
        if (locator==null)
            locator = new Locator(getActivity());
        if (filterListener==null)
            filterListener = new FilterButtonListener(this, getActivity());

        CategoryDAO categoryDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType())).getCategoryDAO();

        edt_filter_radius.setOnEditorActionListener(filterListener);

        //Set spinner content
        List<Category> categories = categoryDAO.findAll();//dataBase.getAllCategories();
        categories.add(0, new Category(getResources().getString(R.string.all), true));
        spi_filter_category.setOnItemSelectedListener(this);
        ArrayAdapter adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_filter_category.setAdapter(adapter);

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
