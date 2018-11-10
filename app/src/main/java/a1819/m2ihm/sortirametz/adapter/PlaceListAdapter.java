package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Place;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListHolder>{

    List<Place> places;

    public PlaceListAdapter(List<Place> places) {
        this.places = places;
    }

    @Override
    public PlaceListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_card, parent, false);
        return new PlaceListHolder(view);
    }

    @Override
    public void onBindViewHolder(PlaceListHolder holder, int position) {
        Place place = places.get(position);
        holder.bind(place);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }

    public void removeItem(int position) {
        places.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Place place, int position) {
        places.add(position, place);
        notifyItemInserted(position);
    }

    public List<Place> getPlaces() {
        return places;
    }

    public Place getPlace(int position) {
        return places.get(position);
    }
}