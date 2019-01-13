package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public class ListPlaceAdapter extends ListAdapter<Recyclerable> {
    public ListPlaceAdapter(Context context, List<Recyclerable> elements) {
        super(context, elements);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        View view = getView(parent, viewType);
        if (viewType==LAYOUT_HEADER)
            viewHolder = new CategoryHeaderListHolder(view);
        else
            viewHolder = new PlaceListHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType()==LAYOUT_HEADER){
            Category category = (Category) elements.get(position);
            ((CategoryHeaderListHolder)holder).bind(category);
        }else {
            Place place = (Place) elements.get(position);
            ((PlaceListHolder)holder).bind(place);
        }
    }

    @Override
        public void removeElementFromDatabase(@NonNull Recyclerable element) {
        Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType()))
                .getPlaceDAO().delete((Place)element);
    }

    private View getView(@NonNull ViewGroup parent, int viewType) {
        if (viewType == LAYOUT_HEADER)
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_header, parent, false);
        else
            return LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_place, parent, false);
    }
}
