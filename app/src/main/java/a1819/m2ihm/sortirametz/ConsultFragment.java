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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.*;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.Objects;

import static android.app.Activity.RESULT_CANCELED;


public class ConsultFragment extends Fragment {

    public static final String APP_TAG = "VisiteAMetz";
    public static final AbstractDAOFactory.FactoryType FACTORY_TYPE = AbstractDAOFactory.FactoryType.SQLite;
    static ListAdapter adapter;

    public @BindView(R.id.linearLayout) FrameLayout mainLayout;
    public @BindView(R.id.swiperefresh) SwipeRefreshLayout layout;
    public @BindView(R.id.list) RecyclerView list;
    public @BindView(R.id.addButton) ImageButton addButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_consult, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        PlaceDAO placeDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), FACTORY_TYPE)).getPlaceDAO();

        //Set the layout for swipe-to-refresh
        layout.setOnRefreshListener(new RefreshListener(this));

        //Set the adapter which set items and item's holder
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        adapter = new ListAdapter(this.getContext(), placeDAO.findAllGroupByCategory());
        list.setAdapter(adapter);

        //Set the callback for swipe on left and right
        new ItemTouchHelper(new ItemTouchHelperCallback(
                new SwipeListener(this, adapter), ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT))
                .attachToRecyclerView(list);

        addButton.setOnClickListener(new AddButtonListener(this.getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==PlaceActivity.RESULT_EDIT || resultCode ==RESULT_CANCELED)
            Objects.requireNonNull(list.getAdapter()).notifyDataSetChanged();
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
        Intent intent = new Intent(this, MapsFragment.class);
        this.startActivity(intent);
        this.finish();
    }*/

}
