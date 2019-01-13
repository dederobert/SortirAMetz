package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.adapter.ListCategoryAdapter;
import a1819.m2ihm.sortirametz.adapter.ListPlaceAdapter;
import a1819.m2ihm.sortirametz.bdd.dao.CategoryDAO;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.listeners.AddButtonListener;
import a1819.m2ihm.sortirametz.listeners.ItemTouchHelperCallback;
import a1819.m2ihm.sortirametz.listeners.RefreshPlaceListener;
import a1819.m2ihm.sortirametz.listeners.swipe.SwipeCategoryListener;
import a1819.m2ihm.sortirametz.listeners.swipe.SwipeListener;
import a1819.m2ihm.sortirametz.listeners.swipe.SwipePlaceListener;
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
import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class ListFragment extends Fragment {

    public enum DisplayMode {
        PLACE,
        CATEGORY
    }

    private PlaceDAO placeDAO;
    private CategoryDAO categoryDAO;

    private ListAdapter adapter;

    public @BindView(R.id.lyt_consult) FrameLayout mainLayout;
    public @BindView(R.id.swp_list) SwipeRefreshLayout layout;
    public @BindView(R.id.rcv_list) RecyclerView list;
    public @BindView(R.id.addButton) ImageButton addButton;
    @NotNull
    public DisplayMode displayMode = DisplayMode.PLACE;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Set the layout for swipe-to-refresh
        layout.setOnRefreshListener(new RefreshPlaceListener(this));

        placeDAO = AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType())
                .getPlaceDAO();

        categoryDAO = AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType()).getCategoryDAO();

        //Set the adapter which set items and item's holder
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

        updateAdapter();
        addButton.setOnClickListener(new AddButtonListener(this));
    }

    public void updateAdapter() {
        if (displayMode.equals(DisplayMode.PLACE))
            adapter = new ListPlaceAdapter(this.getContext(), placeDAO.findAllGroupByCategory());
        else
            adapter = new ListCategoryAdapter(this.getContext(), categoryDAO.findAll());
        list.setAdapter(adapter);

        //Set the callback for swipe on left and right
        SwipeListener swipeListener = displayMode.equals(DisplayMode.PLACE)?new SwipePlaceListener(this, adapter):new SwipeCategoryListener(this, adapter);
        new ItemTouchHelper(new ItemTouchHelperCallback( swipeListener, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT)).attachToRecyclerView(list);
    }

    public ListAdapter getAdapter() {
        return adapter;
    }
}
