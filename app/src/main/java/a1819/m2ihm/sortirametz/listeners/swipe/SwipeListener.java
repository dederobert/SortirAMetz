package a1819.m2ihm.sortirametz.listeners.swipe;

import a1819.m2ihm.sortirametz.ListFragment;
import a1819.m2ihm.sortirametz.listeners.ItemTouchHelperCallback;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.adapter.PlaceListHolder;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Swipe listener is use to handle the right swipe to remove
 */
public abstract class SwipeListener<T extends Recyclerable> implements ItemTouchHelperCallback.RecyclerItemTouchHelperListener {
    private final ListFragment activity;
    private final ListAdapter<T> adapter;

    public SwipeListener(ListFragment listFragment, ListAdapter<T> adapter) {
        this.activity = listFragment;
        this.adapter = adapter;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, final int position) {
        if (viewHolder instanceof PlaceListHolder) {
            if (adapter.getElement(position).getType().equals(Recyclerable.Type.PLACE)){
                String name = getName(position);
                //If swipe to left => delete else Edit
                if (direction == ItemTouchHelper.LEFT)
                    removeItem(viewHolder, name);
                else
                    editItem(viewHolder);
            }
        }
    }

    protected abstract String getName(int position);

    protected abstract void editItem(RecyclerView.ViewHolder viewHolder);

    protected abstract void removeItem(RecyclerView.ViewHolder viewHolder, String name);

    public ListFragment getActivity() {
        return activity;
    }

    public ListAdapter<T> getAdapter() {
        return this.adapter;
    }
}
