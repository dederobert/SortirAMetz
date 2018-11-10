package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.adapter.PlaceListAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;

public class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    ConsultActivity consultActivity;

    public RefreshListener(ConsultActivity consultActivity) {
        this.consultActivity = consultActivity;
    }

    @Override
    public void onRefresh() {
        Log.i(ConsultActivity.APP_TAG, "onRefresh called from SwipeRefreshLayout");
        ((PlaceListAdapter)this.consultActivity.list.getAdapter()).updateItems(this.consultActivity.getDataBase().getAllPlaces());
        this.consultActivity.layout.setRefreshing(false);
    }
}
