package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.ConsultActivity;
import android.content.Intent;
import android.view.View;

public class AddButtonListner implements View.OnClickListener {

    private ConsultActivity activity;

    public AddButtonListner(ConsultActivity consultActivity) {
        this.activity = consultActivity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, PlaceActivity.class);
        activity.startActivity(intent);
    }
}
