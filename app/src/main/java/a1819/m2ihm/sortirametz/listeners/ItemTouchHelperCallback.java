package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.adapter.SwipeableListHolder;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
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
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder instanceof SwipeableListHolder ) {
            final View foregroundView =  ((SwipeableListHolder)viewHolder).viewForeground;
            getDefaultUIUtil().onSelected(foregroundView);
        }
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof SwipeableListHolder) {
            final View foregroundView = ((SwipeableListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof SwipeableListHolder) {
            final View foregroundView = ((SwipeableListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().clearView(foregroundView);
        }
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof SwipeableListHolder) {
            final View foregroundView = ((SwipeableListHolder) viewHolder).viewForeground;
            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        }
    }


    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        //Call onSwiped from SwipeListener interface
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    public RecyclerItemTouchHelperListener getListener() {
        return listener;
    }

    public void setListener(RecyclerItemTouchHelperListener listener) {
        this.listener = listener;
    }

    /**
     * Swipe listener interface
     */
    public interface RecyclerItemTouchHelperListener {
         void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
