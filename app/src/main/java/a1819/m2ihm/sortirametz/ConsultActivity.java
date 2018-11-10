package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.listeners.AddButtonListner;
import a1819.m2ihm.sortirametz.listeners.ItemTouchHelperCallback;
import a1819.m2ihm.sortirametz.listeners.RefreshListener;
import a1819.m2ihm.sortirametz.listeners.SwipeListener;
import a1819.m2ihm.sortirametz.adapter.PlaceListAdapter;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


public class ConsultActivity extends AppCompatActivity {

    public static final String APP_TAG = "VisiteAMetz";
    private DataBase dataBase;
    private ImageButton addButton;

    public FrameLayout mainLayout;
    public SwipeRefreshLayout layout;
    public RecyclerView list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        dataBase = new DataBase(this);

        mainLayout = (FrameLayout) findViewById(R.id.linearLayout);

        layout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(new RefreshListener(this));

        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        PlaceListAdapter adapter = new PlaceListAdapter(dataBase.getAllPlaces());
        list.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelperCallback(new SwipeListener(this, adapter))).attachToRecyclerView(list);

        addButton = (ImageButton) findViewById(R.id.addButton);
        addButton.setOnClickListener(new AddButtonListner(this));
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
        //super.onActivityResult(requestCode, resultCode, data);
        list.getAdapter().notifyDataSetChanged();
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
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMap() {
        Intent intent = new Intent(this, MapsActivity.class);
        this.startActivity(intent);
        this.finish();
    }
}
