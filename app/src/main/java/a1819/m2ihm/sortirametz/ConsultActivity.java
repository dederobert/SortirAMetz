package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.listeners.AddButtonListener;
import a1819.m2ihm.sortirametz.listeners.ItemTouchHelperCallback;
import a1819.m2ihm.sortirametz.listeners.RefreshListener;
import a1819.m2ihm.sortirametz.listeners.SwipeListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import java.util.Objects;


public class ConsultActivity extends AppCompatActivity {

    public static final String APP_TAG = "VisiteAMetz";
    public static final AbstractDAOFactory.FactoryType FACTORY_TYPE = AbstractDAOFactory.FactoryType.SQLite;
    static ListAdapter adapter;

    public FrameLayout mainLayout;
    public SwipeRefreshLayout layout;
    public RecyclerView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        PlaceDAO placeDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(this, FACTORY_TYPE)).getPlaceDAO();
        mainLayout = findViewById(R.id.linearLayout);

        //Set the layout for swipe-to-refresh
        layout = findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(new RefreshListener(this));

        //Set the adapter which set items and item's holder
        list = findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ListAdapter(this, placeDAO.findAllGroupByCategory());
        list.setAdapter(adapter);

        //Set the callback for swipe on left and right
        new ItemTouchHelper(new ItemTouchHelperCallback(
                new SwipeListener(this, adapter), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT))
                .attachToRecyclerView(list);



        ImageButton addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new AddButtonListener(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Permet d'ajouter un menu en haut à droite
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PlaceActivity.RESULT_EDIT || resultCode ==RESULT_CANCELED)
            Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //gestion des cliques dans le menu
        switch (item.getItemId()){
            case R.id.menu_consult:
                return true;
            case R.id.menu_map:
                goToMap();
                return true;
            case R.id.menu_setting:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
        this.finish();
    }

}
