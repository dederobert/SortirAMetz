package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsFragment;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.models.Category;
import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class FilterButtonListener implements View.OnClickListener, TextView.OnEditorActionListener {

    private final MapsFragment mapsFragment;
    private final Activity activity;

    public FilterButtonListener(MapsFragment mapsFragment, Activity activity) {
        this.mapsFragment = mapsFragment;
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
       filter();
    }

    public void filter() {
        String entry = mapsFragment.getRadius();
        int radius = 200;
        if (entry!=null && !entry.equals(""))
            try {
                radius = Integer.parseInt(entry);
            }catch (NumberFormatException e) {
                Toast.makeText(this.activity,
                        this.mapsFragment.getResources().getString(R.string.too_long_number),
                        Toast.LENGTH_LONG).show();
            }

        Category category = mapsFragment.selectedCategory;

        mapsFragment.locator.circle.setRadius(PreferencesHelper.INSTANCE.getUnit(this.activity).convertToMeter(
                radius));
        mapsFragment.locator.mapFilter.setCategory(category);
        mapsFragment.locator.mapFilter.setRadius(radius);
        mapsFragment.locator.mapFilter.filterMarker();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId != 0 || event.getAction()==KeyEvent.ACTION_DOWN) {
            filter();
        }
        return false;
    }
}
