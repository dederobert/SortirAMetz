package a1819.m2ihm.sortirametz.listeners.swipe;

import a1819.m2ihm.sortirametz.CategoryActivity;
import a1819.m2ihm.sortirametz.ListFragment;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.models.Category;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.Objects;

public class SwipeCategoryListener extends SwipeListener<Category> {

    public SwipeCategoryListener(ListFragment listFragment, ListAdapter<Category> adapter) {
        super(listFragment, adapter);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        super.onSwiped(viewHolder, direction, position);
        Log.d("SwipeCateg", "swiped");
    }

    @Override
    protected String getName(int position) {
        return getAdapter().getElement(position).getDescription();
    }

    @Override
    protected void editItem(RecyclerView.ViewHolder viewHolder) {
        Log.d("SwipeCateg", "edit");
        Category category = getAdapter().getElement(viewHolder.getAdapterPosition());
        Intent intent = new Intent(this.getFragment().getActivity(), CategoryActivity.class);
        intent.putExtra("categoryId",category.getId()).putExtra("position", viewHolder.getAdapterPosition());
        Objects.requireNonNull(this.getFragment().getActivity())
                .startActivityForResult(intent, CategoryActivity.REQUEST_EDIT);
    }

    @Override
    protected void removeItem(RecyclerView.ViewHolder viewHolder, String name) {
        //The deleted place and its index
        final Category deletedCategory = getAdapter().getElement(viewHolder.getAdapterPosition());
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
                getAdapter().restoreItem(deletedCategory, deleteIndex);
            }
        });

        //When the snackbar disappear, the place will remove from database
        snackbar.addCallback(new Snackbar.Callback(){
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                if (event != Snackbar.Callback.DISMISS_EVENT_ACTION)
                    getAdapter().removeElementFromDatabase(deletedCategory);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
