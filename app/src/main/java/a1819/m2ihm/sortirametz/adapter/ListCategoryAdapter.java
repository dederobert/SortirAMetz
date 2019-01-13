package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import a1819.m2ihm.sortirametz.models.Category;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

public class ListCategoryAdapter extends ListAdapter<Category> {
    public ListCategoryAdapter(Context context, List<Category> elements) {
        super(context, elements);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryListHolder(getView(parent));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)  {
            Category category = elements.get(position);
            ((CategoryListHolder)holder).bind(category);
    }

    @Override
    public void removeElementFromDatabase(@NonNull Category element) {
        Objects.requireNonNull(AbstractDAOFactory.getFactory(getContext(), ValueHelper.INSTANCE.getFactoryType()))
                .getCategoryDAO().delete(element);
    }

    private View getView(@NonNull ViewGroup parent) {
        return LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_category, parent, false);
    }
}
