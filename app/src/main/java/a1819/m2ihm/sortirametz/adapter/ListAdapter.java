package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.ConsultActivity;
import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.bdd.DataBase;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private static final int LAYOUT_HEADER= 0;
    private static final int LAYOUT_CHILD= 1;

    List<Recyclerable> places;
    ConsultActivity context;

    public ListAdapter(ConsultActivity context, List<Recyclerable> places) {
        this.places = places;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(places.get(position).isHeader()) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType==LAYOUT_HEADER){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.header_card, parent, false);
            viewHolder = new CategoryListHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.place_card, parent, false);
            viewHolder = new PlaceListHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==LAYOUT_HEADER){
            Category category = (Category) places.get(position);
            ((CategoryListHolder)holder).bind(category);
        }else {
            Place place = (Place) places.get(position);
            ((PlaceListHolder)holder).bind(place);
        }

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

    public List<Recyclerable> getPlaces() {
        return places;
    }

    public Recyclerable getPlace(int position) {
        return places.get(position);
    }

    public void removeItemFromDatabase(Place place) {
        context.getDataBase().deletePlace(place);
    }

    public void updateItems(List<Recyclerable> places) {
        this.places = places;
        notifyDataSetChanged();
    }

    public void insertPlace(Place place) {
        places.add(place);
        notifyItemInserted(places.size()-1);
    }
}