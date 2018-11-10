package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Place;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListHolder>{

    List<Place> places;
    ConsultActivity context;

    public PlaceListAdapter(ConsultActivity context, List<Place> places) {
        this.places = places;
        this.context = context;
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

    public void removeItemFromDatabase(Place place) {
        context.getDataBase().deletePlace(place);
    }

    public void updateItems(List<Place> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    public void insertPlace(Place place) {
        places.add(place);
        notifyItemInserted(places.size()-1);
    }
}
