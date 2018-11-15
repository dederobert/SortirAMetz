package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

import java.util.Objects;

public class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    private ConsultActivity consultActivity;

    public RefreshListener(ConsultActivity consultActivity) {
        this.consultActivity = consultActivity;
    }

    @Override
    public void onRefresh() {
        Log.i(ConsultActivity.APP_TAG, "onRefresh called from SwipeRefreshLayout");
        ((ListAdapter) Objects.requireNonNull(this.consultActivity.list.getAdapter()))
                .updateItems(
                        Objects.requireNonNull(AbstractDAOFactory.getFactory(consultActivity, ConsultActivity.FACTORY_TYPE)).getPlaceDAO()
                                .findAllGroupByCategory()
                        );
        this.consultActivity.layout.setRefreshing(false);
    }
}
