package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.MapsActivity;
import a1819.m2ihm.sortirametz.models.Category;
import android.view.View;

public class FilterButtonListener implements View.OnClickListener {

    private final MapsActivity activity;

    public FilterButtonListener(MapsActivity mapsActivity) {
        this.activity = mapsActivity;
    }

    @Override
    public void onClick(View v) {
        int radius = Integer.parseInt(activity.edt_filter_radius.getText().toString());
        Category category = activity.selectedCategory;

        activity.locator.circle.setRadius(radius);
    }
}
