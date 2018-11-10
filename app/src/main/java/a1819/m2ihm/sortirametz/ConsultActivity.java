package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.listeners.ItemTouchHelperCallback;
import a1819.m2ihm.sortirametz.listeners.RefreshListener;
import a1819.m2ihm.sortirametz.view.PlaceListAdapter;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Map;


public class ConsultActivity extends AppCompatActivity {

    public static final String APP_TAG = "VisiteAMetz";
    private DataBase dataBase;

    public SwipeRefreshLayout layout;
    public RecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult);
        dataBase = new DataBase(this);

        layout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        layout.setOnRefreshListener(new RefreshListener(this));

        list = (RecyclerView) findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        PlaceListAdapter adapter = new PlaceListAdapter(dataBase.getAllPlaces());
        list.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelperCallback(adapter)).attachToRecyclerView(list);

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
