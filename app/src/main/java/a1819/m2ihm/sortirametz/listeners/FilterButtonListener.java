package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.models.Category;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

public class FilterButtonListener implements View.OnClickListener, TextView.OnEditorActionListener {

    private final MapsActivity activity;

    public FilterButtonListener(MapsActivity mapsActivity) {
        this.activity = mapsActivity;
    }

    @Override
    public void onClick(View v) {
       filter();
    }

    public void filter() {
        String entry = activity.getRadius();
        int radius = 200;
        if (entry!=null && !entry.equals(""))
            try {
                radius = Integer.parseInt(entry);
            }catch (NumberFormatException e) {
                Toast.makeText(this.activity,
                        this.activity.getResources().getString(R.string.too_long_number),
                        Toast.LENGTH_LONG).show();
            }

        Category category = activity.selectedCategory;

        activity.locator.circle.setRadius(PreferencesHelper.INSTANCE.getUnit(this.activity).convertToMeter(
                radius));
        activity.locator.mapFilter.setCategory(category);
        activity.locator.mapFilter.setRadius(radius);
        activity.locator.mapFilter.filterMarker();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId != 0 || event.getAction()==KeyEvent.ACTION_DOWN) {
            filter();
        }
        return false;
    }
}
