package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher, View.OnFocusChangeListener {


    public static final int RESULT_ADD = 10;
    public static final int RESULT_EDIT = 11;

    private DataBase dataBase;
    private RelativeLayout place_main_layout;
    private EditText edt_name;
    private EditText edt_coord;
    //private Button btn_coord;
    private EditText edt_address;
    private EditText edt_description;
    private EditText edt_icon;
    private ImageView img_icon;
    private Button btn_save;

    private PlaceActivity activity;

    private Category selectedCategory;
    private boolean add;
    private Place place = null;
    private final int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        activity = this;
        long placeId = getIntent().getLongExtra("placeId", -1);
        this.add = (placeId==-1);

        dataBase = new DataBase(this);
        if (!add) place = dataBase.getPlace(placeId);
        else place = new Place();

        if (add)
            startPicker();

        String title = add?getResources().getString(R.string.add_title)
                :(getResources().getString(R.string.edit_title) + " "+place.getName());
        setTitle(title);

        place_main_layout = findViewById(R.id.place_main_layout);
        edt_name = findViewById(R.id.edt_name);
        edt_coord = findViewById(R.id.edt_coord);
        edt_coord.setOnFocusChangeListener(this);
        edt_address = findViewById(R.id.edt_address);
        Spinner spi_category = findViewById(R.id.spi_category);
        edt_description = findViewById(R.id.edt_description);
        edt_icon = findViewById(R.id.edt_icon);
        edt_icon.addTextChangedListener(this);
        img_icon = findViewById(R.id.img_icon);
        btn_save = findViewById(R.id.btn_save);
        Button btn_cancel = findViewById(R.id.btn_cancel);

        //Set spinner content
        List<Category> categories = dataBase.getAllCategories();
        spi_category.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_category.setAdapter(adapter);

        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);

        if (!add) {
            edt_name.setText(place.getName());
            edt_coord.setText(place.getLatitude()+", "+place.getLongitude());
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
                com.google.android.gms.location.places.Place place = PlacePicker.getPlace(data, this);
                edt_coord.setText(place.getLatLng().latitude + ", " +place.getLatLng().longitude);
                if (add) edt_address.setText(place.getAddress());
                if (add) edt_name.setText(place.getName());
                this.place.setLatitude((float) place.getLatLng().latitude);
                this.place.setLongitude((float) place.getLatLng().longitude);
                Toast.makeText(this, R.string.place_picked, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_save)) {
            if (add) {
                place.setName(edt_name.getText().toString());
                place.setAddress(edt_address.getText().toString());
                place.setCategory(selectedCategory);
                place.setDescription(edt_description.getText().toString());
                place.setIcon(edt_icon.getText().toString());
                dataBase.addPlace(place);
                ConsultActivity.adapter.insertPlace(place);
                this.setResult(RESULT_OK);
            } else {
                place.setName(edt_name.getText().toString());
                place.setAddress(edt_address.getText().toString());
                place.setCategory(selectedCategory);
                place.setDescription(edt_description.getText().toString());
                place.setIcon(edt_icon.getText().toString());
                dataBase.updatePlace(place);
                this.setResult(RESULT_OK);
            }
            this.finish();
        }else {
            setResult(RESULT_CANCELED);
            this.finish();
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
