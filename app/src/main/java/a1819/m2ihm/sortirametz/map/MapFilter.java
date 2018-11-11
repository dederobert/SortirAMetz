package a1819.m2ihm.sortirametz.map;

import a1819.m2ihm.sortirametz.models.Category;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class MapFilter {
    private final List<Locator.CustomMarker> markers;
    private Location currentLocation;
    private float radius = 200;
    private Category category = null;


    public  MapFilter(List<Locator.CustomMarker> markers) {
        this.markers = markers;
    }

    /**
     * Apply filter on marker
     * @version 1.4-alpha
     */
    public void filterMarker() {
        if (currentLocation!= null) {
            for (Locator.CustomMarker marker : markers) {
                float[] distance = new float[1];
                Location.distanceBetween(
                        currentLocation.getLatitude(),
                        currentLocation.getLongitude(),
                        marker.getMarker().getPosition().latitude,
                        marker.getMarker().getPosition().longitude,
                        distance
                );
                boolean visible =
                        (category != null && (category.isMockCategory() || marker.getCategory().equals(category)))
                                && (distance[0] <= radius);
                marker.getMarker().setVisible(visible);
            }
        }
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }
}
