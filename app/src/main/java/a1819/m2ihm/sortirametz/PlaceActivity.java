package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PlaceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, TextWatcher {


    public static final int RESULT_ADD = 10;
    public static final int RESULT_EDIT = 11;

    private DataBase dataBase;
    private RelativeLayout place_main_layout;
    private EditText edt_name;
    private EditText edt_address;
    private EditText edt_description;
    private EditText edt_icon;
    private ImageView img_icon;
    private Button btn_save;

    private PlaceActivity activity;

    private Category selectedCategory;
    private boolean add;
    private Place place = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        activity = this;
        int placeId = getIntent().getIntExtra("placeId", -1);
        this.add = (placeId==-1);


        dataBase = new DataBase(this);
        if (!add) place = dataBase.getPlace(placeId);

        place_main_layout = findViewById(R.id.place_main_layout);
        edt_name = findViewById(R.id.edt_name);
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
            edt_address.setText(place.getAddress());
            edt_description.setText(place.getDescription());
            edt_icon.setText(place.getIcon());
            if (place.getIcon()!=null && !place.getIcon().equals(""))
                Picasso.get().load(place.getIcon()).into(img_icon);
            spi_category.setSelection(categories.indexOf(place.getCategory()));
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_save)) {
            if (add){
                place = new Place(edt_name.getText().toString(), 0, 0,
                        edt_address.getText().toString(), selectedCategory,
                        edt_description.getText().toString(), edt_icon.getText().toString());
                dataBase.addPlace(place);
                ConsultActivity.adapter.insertPlace(place);
                this.setResult(RESULT_ADD);
            }else {
                place.setName(edt_name.getText().toString());
                place.setAddress(edt_address.getText().toString());
                place.setCategory(selectedCategory);
                place.setDescription(edt_description.getText().toString());
                place.setIcon(edt_icon.getText().toString());
                dataBase.updatePlace(place);
                this.setResult(RESULT_EDIT);
            }
            this.finish();
        }else {
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
}
