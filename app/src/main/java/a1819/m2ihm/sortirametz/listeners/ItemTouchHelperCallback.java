package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.adapter.PlaceListHolder;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class ItemTouchHelperCallback extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public ItemTouchHelperCallback(RecyclerItemTouchHelperListener listener, int direction) {
        super(0, direction);
        this.listener = listener;
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null && viewHolder instanceof  PlaceListHolder) {
            final View foregroundView =  ((PlaceListHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof PlaceListHolder) {
            final View foregroundView = ((PlaceListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof PlaceListHolder) {
            final View foregroundView = ((PlaceListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof PlaceListHolder) {
            final View foregroundView = ((PlaceListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        //Call onSwiped from SwipeListener interface
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    /**
     * Swipe listener interface
     */
    public interface RecyclerItemTouchHelperListener {
         void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
