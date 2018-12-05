package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultFragment;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import java.util.Objects;

public class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    private ConsultFragment consultFragment;

    public RefreshListener(ConsultFragment consultFragment) {
        this.consultFragment = consultFragment;
    }

    @Override
    public void onRefresh() {
        Log.i(ConsultFragment.APP_TAG, "onRefresh called from SwipeRefreshLayout");
        ((ListAdapter) Objects.requireNonNull(this.consultFragment.list.getAdapter()))
                .updateItems(
                        Objects.requireNonNull(AbstractDAOFactory.getFactory(consultFragment.getActivity(), ConsultFragment.FACTORY_TYPE)).getPlaceDAO()
                                .findAllGroupByCategory()
                        );
        this.consultFragment.layout.setRefreshing(false);
    }
}
