package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Category;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class CategoryListHolder extends RecyclerView.ViewHolder {

    TextView txt_category_name;

    public CategoryListHolder(@NonNull View itemView) {
        super(itemView);
        txt_category_name = itemView.findViewById(R.id.txt_category_name);
    }

    void bind(Category category) {
        txt_category_name.setText(category.getDescription());
    }
}
