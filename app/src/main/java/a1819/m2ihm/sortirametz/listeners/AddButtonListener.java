package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.ConsultActivity;
import android.content.Intent;
import android.view.View;

public class AddButtonListener implements View.OnClickListener {

    private ConsultActivity activity;

    public AddButtonListener(ConsultActivity consultActivity) {
        this.activity = consultActivity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, PlaceActivity.class);
        activity.startActivityForResult(intent, PlaceActivity.RESULT_ADD);
    }
}
