package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import a1819.m2ihm.sortirametz.models.Category;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

class CategoryHeaderListHolder extends RecyclerView.ViewHolder{

    private TextView lbl_header;

    CategoryHeaderListHolder(View itemView) {
        super(itemView);
        lbl_header = itemView.findViewById(R.id.lbl_header);
    }

    void bind(Category category) {
       lbl_header.setText(category.getDescription());
    }
}
