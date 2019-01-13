package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.map.Locator;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.utils.UniqueId;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PlaceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, TextWatcher, View.OnFocusChangeListener {


    public static final int REQUEST_ADD = UniqueId.INSTANCE.nextValue();
    public static final int REQUEST_EDIT = UniqueId.INSTANCE.nextValue();

    private PlaceDAO placeDAO;
    @BindView(R.id.place_main_layout) RelativeLayout place_main_layout;
    @BindView(R.id.edt_name) EditText edt_name;
    @BindView(R.id.edt_coord) EditText edt_coord;
    @BindView(R.id.edt_address) EditText edt_address;
    @BindView(R.id.edt_description) EditText edt_description;
    @BindView(R.id.edt_icon) EditText edt_icon;
    @BindView(R.id.img_icon) ImageView img_icon;
    @BindView(R.id.spi_category) Spinner spi_category;

    private PlaceActivity activity;

    private Category selectedCategory;
    private boolean addMode;
    private Place place = null;
    private final int PLACE_PICKER_REQUEST = 1;

    @OnClick(R.id.btn_save) void save() {
        place.setName(edt_name.getText().toString());
        place.setAddress(edt_address.getText().toString());
        place.setCategory(selectedCategory);
        place.setDescription(edt_description.getText().toString());
        place.setIcon(edt_icon.getText().toString());

        if (addMode) {
            placeDAO.create(place);
            getIntent().putExtra("placeId", place.getId());
        } else
            placeDAO.update(place);

        this.setResult(RESULT_OK);
        this.finish();
    }

    @OnClick(R.id.btn_cancel) void cancel() {
        setResult(RESULT_CANCELED);
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        ButterKnife.bind(this);

        activity = this;
        long placeId = getIntent().getLongExtra("placeId", -1);
        this.addMode = (placeId==-1);

        AbstractDAOFactory factory = Objects.requireNonNull(AbstractDAOFactory.getFactory(this, ValueHelper.INSTANCE.getFactoryType()));
        placeDAO = factory.getPlaceDAO();
        CategoryDAO categoryDAO = factory.getCategoryDAO();

        if (!addMode) place = placeDAO.find(placeId);
        else place = new Place();

        if (addMode)
            startPicker();

        String title = addMode ?getResources().getString(R.string.add_title)
                :(getResources().getString(R.string.edit_title) + " "+place.getName());
        setTitle(title);

        edt_coord.setOnFocusChangeListener(this);
        edt_icon.addTextChangedListener(this);

        //Set spinner content
        List<Category> categories = categoryDAO.findAll();
        spi_category.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_category.setAdapter(adapter);

        if (!addMode) {
            edt_name.setText(place.getName());
            edt_coord.setText(String.format(Locale.FRANCE,"%f, %f",place.getLatitude(),place.getLongitude()));
            edt_address.setText(place.getAddress());
            edt_description.setText(place.getDescription());
            edt_icon.setText(place.getIcon());
            if (place.getIcon()!=null && !place.getIcon().equals(""))
                Picasso.get().load(place.getIcon()).into(img_icon);
            spi_category.setSelection(categories.indexOf(place.getCategory()));
        }
    }

    private void startPicker() {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        builder.setLatLngBounds(LatLngBounds.builder().include(Locator.METZ_LATITUDE_LONGITUDE).build());
        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            Toast.makeText(this, "Error map picker cannot open", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(this, data);
                edt_coord.setText(String.format(Locale.FRANCE,"%f, %f",place.getLatLng().latitude, place.getLatLng().longitude));
                if (addMode) edt_address.setText(place.getAddress());
                if (addMode) edt_name.setText(place.getName());
                this.place.setLatitude((float) place.getLatLng().latitude);
                this.place.setLongitude((float) place.getLatLng().longitude);
                Toast.makeText(this, R.string.place_picked, Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedCategory = (Category) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        selectedCategory = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        final Picasso.Builder builder = new Picasso.Builder(this);
        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                Snackbar.make(activity.place_main_layout,R.string.error_image_load,Snackbar.LENGTH_LONG).show();
            }
        });
        builder.build().load(Uri.parse(edt_icon.getText().toString())).into(img_icon);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            startPicker();
        }
    }
}
