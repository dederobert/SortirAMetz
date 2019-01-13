package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.CategoryActivity;
import a1819.m2ihm.sortirametz.ListFragment;
import a1819.m2ihm.sortirametz.PlaceActivity;
import a1819.m2ihm.sortirametz.models.Category;
import android.content.Intent;
import android.view.View;

import java.util.Objects;

public class AddButtonListener implements View.OnClickListener {

    private ListFragment fragment;

    public AddButtonListener(ListFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (fragment.displayMode) {
            case PLACE: {
                intent = new Intent(fragment.getContext(), PlaceActivity.class);
                Objects.requireNonNull(fragment.getActivity())
                        .startActivityForResult(intent, PlaceActivity.REQUEST_ADD);
                break;
            }
            case CATEGORY: {
                intent = new Intent(fragment.getContext(), CategoryActivity.class);
                Objects.requireNonNull(fragment.getActivity())
                        .startActivityForResult(intent, CategoryActivity.REQUEST_ADD);
                break;
            }
        }
    }
}
