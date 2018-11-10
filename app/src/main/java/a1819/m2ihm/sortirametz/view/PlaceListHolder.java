package a1819.m2ihm.sortirametz.view;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

public class PlaceListHolder extends RecyclerView.ViewHolder{

    private ImageView placeIcon;
    private TextView placeNameView;
    private TextView placeAddressView;

    public PlaceListHolder(View itemView) {
        super(itemView);
        placeIcon = (ImageView) itemView.findViewById(R.id.placeIcon);
        placeNameView = (TextView) itemView.findViewById(R.id.placeName);
        placeAddressView = (TextView) itemView.findViewById(R.id.placeAddress);
    }

    public void bind(Place place) {
        Picasso.get().load(Uri.parse(place.getIcon())).into(placeIcon);
        placeNameView.setText(place.getName());
        placeAddressView.setText(place.getAddress());
    }
}
