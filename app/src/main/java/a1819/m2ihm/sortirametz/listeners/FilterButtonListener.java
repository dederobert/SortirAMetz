package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import a1819.m2ihm.sortirametz.helpers.PreferencesHelper;
import a1819.m2ihm.sortirametz.models.Category;
import android.view.View;

public class FilterButtonListener implements View.OnClickListener {

    private final MapsActivity activity;

    public FilterButtonListener(MapsActivity mapsActivity) {
        this.activity = mapsActivity;
    }

    @Override
    public void onClick(View v) {
        String entry = activity.edt_filter_radius.getText().toString();
        int radius;
        if (entry!=null && !entry.equals(""))
            radius = Integer.parseInt(entry);
        else
            radius = 200;
        Category category = activity.selectedCategory;

        activity.locator.circle.setRadius(PreferencesHelper.INSTANCE.getUnit(this.activity).convertToMeter(
                radius));
        activity.locator.mapFilter.setCategory(category);
        activity.locator.mapFilter.setRadius(radius);
        activity.locator.mapFilter.filterMarker();
    }
}
