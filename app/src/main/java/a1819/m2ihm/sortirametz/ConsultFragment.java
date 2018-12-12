package a1819.m2ihm.sortirametz;

import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.bdd.dao.PlaceDAO;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
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

    //TODO SUPPRIMER LA VARIABLE STATIC
    static ListAdapter adapter;

    public @BindView(R.id.lyt_consult) FrameLayout mainLayout;
    public @BindView(R.id.swp_list) SwipeRefreshLayout layout;
    public @BindView(R.id.rcv_list) RecyclerView list;
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

        PlaceDAO placeDAO = Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType()))
                .getPlaceDAO();

        //Set the layout for swipe-to-refresh
        layout.setOnRefreshListener(new RefreshListener(this));

        //Set the adapter which set items and item's holder
        list.setLayoutManager(new LinearLayoutManager(getContext()));
        list.setItemAnimator(new DefaultItemAnimator());
        list.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), DividerItemDecoration.VERTICAL));

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
}
