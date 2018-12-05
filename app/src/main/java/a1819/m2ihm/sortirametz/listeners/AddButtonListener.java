package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.PlaceActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class AddButtonListener implements View.OnClickListener {

    private Activity activity;

    public AddButtonListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(activity, PlaceActivity.class);
        activity.startActivityForResult(intent, PlaceActivity.RESULT_ADD);
    }
}
