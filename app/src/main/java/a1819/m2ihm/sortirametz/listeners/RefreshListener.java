package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
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
        ((ListAdapter)this.consultActivity.list.getAdapter())
                .updateItems(this.consultActivity.getDataBase().getAllPlacesGroupByCategory());
        this.consultActivity.layout.setRefreshing(false);
    }
}
