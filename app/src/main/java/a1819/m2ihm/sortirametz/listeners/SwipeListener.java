package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultFragment;
import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
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
    private final ConsultFragment activity;
    private final ListAdapter adapter;

    public SwipeListener(ConsultFragment consultFragment, ListAdapter adapter) {
        this.activity = consultFragment;
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        if (viewHolder instanceof PlaceListHolder) {
            if (!adapter.getPlace(position).isHeader()){
                //The name of the swiped item
                String name = ((Place)adapter.getPlace(position)).getName();
                //If swipe to left => delete else Edit
                if (direction == ItemTouchHelper.LEFT)
                    removeItem(viewHolder, name);
                else
                    editItem(viewHolder);
            }
        }
    }

    private void editItem(RecyclerView.ViewHolder viewHolder) {
        Place place = ((Place)adapter.getPlace(viewHolder.getAdapterPosition()));
        Intent intent = new Intent(this.activity.getActivity(), PlaceActivity.class);
        intent.putExtra("placeId",place.getId());
        activity.startActivityForResult(intent, PlaceActivity.RESULT_EDIT);
    }

    private void removeItem(RecyclerView.ViewHolder viewHolder, String name) {
        //The deleted place and its index
        final Place deletedPlace = ((Place)adapter.getPlace(viewHolder.getAdapterPosition()));
        final int deleteIndex = viewHolder.getAdapterPosition();

        //Call remove item (it doesn't remove from database)
        adapter.removeItem(deleteIndex);

        //Display a snack bar with undo button
        Snackbar snackbar = Snackbar.
                make(activity.mainLayout,
                        name + " " + activity.getResources().getString(R.string.element_removed),
                        Snackbar.LENGTH_LONG);
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
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                    adapter.removePlaceFromDatabase(deletedPlace);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
