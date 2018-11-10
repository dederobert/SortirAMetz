package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.adapter.PlaceListAdapter;
import a1819.m2ihm.sortirametz.adapter.PlaceListHolder;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class SwipeListener implements ItemTouchHelperCallback.RecyclerItemTouchHelperListener {
    private final ConsultActivity activity;
    private final PlaceListAdapter adapter;

    public SwipeListener(ConsultActivity consultActivity, PlaceListAdapter adapter) {
        this.activity = consultActivity;
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof PlaceListHolder) {
            String name = adapter.getPlace(viewHolder.getAdapterPosition()).getName();

            final Place deletedPlace = adapter.getPlace(viewHolder.getAdapterPosition());
            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);
            Snackbar snackbar = Snackbar.
                    make(activity.mainLayout, name + " " + R.string.element_removed, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.undo, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    adapter.restoreItem(deletedPlace, deleteIndex);
                }
            });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();
        }
    }
}
