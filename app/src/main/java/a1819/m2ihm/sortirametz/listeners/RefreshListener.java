package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
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
        //TODO update
        this.consultActivity.layout.setRefreshing(false);
    }
}
