package a1819.m2ihm.sortirametz.view;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class PlaceListHolder extends RecyclerView.ViewHolder{

    private TextView placeNameView;
    private ImageView placeIcon;

    public PlaceListHolder(View itemView) {
        super(itemView);
        placeIcon = (ImageView) itemView.findViewById(R.id.placeIcon);
        placeNameView = (TextView) itemView.findViewById(R.id.placeName);
    }

    public void bind(Place place) {
        placeIcon.setImageURI(Uri.parse(place.getIcon()));
        placeNameView.setText(place.getName());
    }
}
