package a1819.m2ihm.sortirametz.adapter;

import a1819.m2ihm.sortirametz.R;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

public class SwipeableListHolder extends RecyclerView.ViewHolder {

    public RelativeLayout viewForeground;
    public SwipeableListHolder(@NonNull View itemView) {
        super(itemView);
        viewForeground = itemView.findViewById(R.id.view_foreground);
    }
}
