package a1819.m2ihm.sortirametz.listeners.swipe;

import a1819.m2ihm.sortirametz.ListFragment;
import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.models.Place;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.Objects;

public class SwipePlaceListener extends SwipeListener<Place> {

    public SwipePlaceListener(ListFragment listFragment, ListAdapter adapter) {
        super(listFragment, adapter);
    }

    @Override
    protected String getName(int position) {
        return getAdapter().getElement(position).getName();
    }

    @Override
    protected void editItem(RecyclerView.ViewHolder viewHolder) {
        Place place = getAdapter().getElement(viewHolder.getAdapterPosition());
        Intent intent = new Intent(this.getFragment().getActivity(), PlaceActivity.class);
        intent.putExtra("placeId",place.getId()).putExtra("position", viewHolder.getAdapterPosition());
        Objects.requireNonNull(this.getFragment().getActivity())
                .startActivityForResult(intent, PlaceActivity.REQUEST_EDIT);
    }

    @Override
    protected void removeItem(RecyclerView.ViewHolder viewHolder, String name) {
        //The deleted place and its index
        final Place deletedPlace = getAdapter().getElement(viewHolder.getAdapterPosition());
        final int deleteIndex = viewHolder.getAdapterPosition();

        //Call remove item (it doesn't remove from database)
        getAdapter().removeItem(deleteIndex);

        //Display a snack bar with undo button
        Snackbar snackbar = Snackbar.
                make(getFragment().mainLayout,
                        name + " " + getFragment().getResources().getString(R.string.element_removed),
                        Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.undo, new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getAdapter().restoreItem(deletedPlace, deleteIndex);
            }
        });

        //When the snackbar disappear, the place will remove from database
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                    getAdapter().removeElementFromDatabase(deletedPlace);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
