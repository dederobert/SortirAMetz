package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.adapter.PlaceListAdapter;
import a1819.m2ihm.sortirametz.adapter.PlaceListHolder;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

/**
 * Swipe listener is use to handle the right swipe to remove
 */
public class SwipeListener implements ItemTouchHelperCallback.RecyclerItemTouchHelperListener {
    private final ConsultActivity activity;
    private final PlaceListAdapter adapter;

    public SwipeListener(ConsultActivity consultActivity, PlaceListAdapter adapter) {
        this.activity = consultActivity;
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        if (viewHolder instanceof PlaceListHolder) {
            //The name of the swiped item
            String name = adapter.getPlace(viewHolder.getAdapterPosition()).getName();

            //If swipe to left => delete else Edit
            if (direction == ItemTouchHelper.LEFT)
                removeItem(viewHolder, name);
            else
                editItem(viewHolder);
        }
    }

    private void editItem(RecyclerView.ViewHolder viewHolder) {
        Place place = adapter.getPlace(viewHolder.getAdapterPosition());
        Intent intent = new Intent(this.activity, PlaceActivity.class);
        intent.putExtra("placeId",place.getId());
        activity.startActivityForResult(intent, PlaceActivity.RESULT_EDIT);
    }

    private void removeItem(RecyclerView.ViewHolder viewHolder, String name) {
        //The deleted place and its index
        final Place deletedPlace = adapter.getPlace(viewHolder.getAdapterPosition());
        final int deleteIndex = viewHolder.getAdapterPosition();

        //Call remove item (it doesn't remove from database)
        adapter.removeItem(deleteIndex);

        //Display a snack bar with undo button
        Snackbar snackbar = Snackbar.
                make(activity.mainLayout, name + " " + R.string.element_removed, Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                adapter.restoreItem(deletedPlace, deleteIndex);
            }
        });

        //When the snackbar disappear, the place will remove from database
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                adapter.removeItemFromDatabase(deletedPlace);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
