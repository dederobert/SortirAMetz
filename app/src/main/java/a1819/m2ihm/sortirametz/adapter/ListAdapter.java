package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.Category;
import a1819.m2ihm.sortirametz.models.Place;
import a1819.m2ihm.sortirametz.models.Recyclerable;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public abstract class ListAdapter<T extends Recyclerable> extends RecyclerView.Adapter<RecyclerView.ViewHolder>{


    static final int LAYOUT_HEADER= 0;
    static final int LAYOUT_CHILD= 1;


    List<T> elements;
    private Context context;

    ListAdapter(Context context, List<T> elements) {
        this.elements = elements;
        this.context = context;
    }


    @Override
    public int getItemViewType(int position) {
        if(elements.get(position).getType().equals(Recyclerable.Type.CATEGORY)) {
            return LAYOUT_HEADER;
        }else
            return LAYOUT_CHILD;
    }

    @NonNull
    @Override
    public abstract RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType);

    @Override
    public abstract void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return elements.size();
    }

    public void removeItem(int position) {
        elements.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(T element, int position) {
        elements.add(position, element);
        notifyItemInserted(position);
    }

    public T getElement(int position) {
        return elements.get(position);
    }

    public abstract void removeElementFromDatabase(@NonNull T element);

    public void updateItems(List<T> elements) {
        this.elements = elements;
        notifyDataSetChanged();
    }

    public void updateItem(T elements, int position) {
        if (position == -1 || elements == null) return;
        this.elements.set(position,elements);
        notifyItemChanged(position);
    }

    public void insertElement(T element) {
        elements.add(element);
        notifyItemInserted(elements.size()-1);
    }

    public Context getContext() {
        return context;
    }
}
