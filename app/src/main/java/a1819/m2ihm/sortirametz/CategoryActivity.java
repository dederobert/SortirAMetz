package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.utils.UniqueId;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import java.util.Objects;

public class CategoryActivity extends AppCompatActivity {
    public static final int REQUEST_ADD = UniqueId.INSTANCE.nextValue();
    public static final int REQUEST_EDIT = UniqueId.INSTANCE.nextValue();
    private boolean addMode;
    private Category category;
    private CategoryDAO categoryDAO;

    @BindView(R.id.edt_description) EditText edt_description;

    @OnClick(R.id.btn_save) void save() {
        category.setDescription(edt_description.getText().toString());
        if (addMode) {
            categoryDAO.create(category);
            getIntent().putExtra("categoryId", category.getId());
        }else
            categoryDAO.update(category);

        this.setResult(RESULT_OK);
        this.finish();
    }

    @OnClick(R.id.btn_cancel) void cancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        long categoryId = getIntent().getLongExtra("categoryId", -1);
        this.addMode = (categoryId==-1);

        categoryDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(this, ValueHelper.INSTANCE.getFactoryType())).getCategoryDAO();
        if (!addMode)
            category = categoryDAO.find(categoryId);
        else
            category = new Category();

        String title = addMode ?getResources().getString(R.string.add_title)
                :(getResources().getString(R.string.edit_title) + " "+category.getDescription());
        setTitle(title);

        if (!addMode)
            edt_description.setText(category.getDescription());
    }
}
