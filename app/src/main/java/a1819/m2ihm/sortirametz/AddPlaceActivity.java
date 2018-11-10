package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class AddPlaceActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private DataBase dataBase;

    private EditText edt_add_name;
    private EditText edt_add_address;
    private EditText edt_add_description;
    private EditText edt_add_icon;
    private Button btn_add_add;

    private Category selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        dataBase = new DataBase(this);


        edt_add_name = findViewById(R.id.edt_add_name);
        edt_add_address = findViewById(R.id.edt_add_address);
        Spinner spi_add_category = findViewById(R.id.spi_add_category);
        edt_add_description = findViewById(R.id.edt_add_description);
        edt_add_icon = findViewById(R.id.edt_add_icon);
        btn_add_add = findViewById(R.id.btn_add_add);
        Button btn_add_cancel = findViewById(R.id.btn_add_cancel);

        spi_add_category.setOnItemSelectedListener(this);
        ArrayAdapter<Category> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dataBase.getAllCategories());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spi_add_category.setAdapter(adapter);

        btn_add_add.setOnClickListener(this);
        btn_add_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(btn_add_add)) {
            Place place = new Place(edt_add_name.getText().toString(), 0, 0,
                    edt_add_address.getText().toString(), selectedCategory,
                    edt_add_description.getText().toString(), edt_add_icon.getText().toString());
            dataBase.addPlace(place);
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
}
