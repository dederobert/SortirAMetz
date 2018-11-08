package a1819.m2ihm.sortirametz.view;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PlaceListHolder extends RecyclerView.ViewHolder{

    private TextView placeNameView;

    public PlaceListHolder(View itemView) {
        super(itemView);
        placeNameView = (TextView) itemView.findViewById(R.id.placeName);
    }

    public void bind(Place place) {
        placeNameView.setText(place.getName());
    }
}
