package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ListFragment;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.adapter.ListCategoryAdapter;
import a1819.m2ihm.sortirametz.adapter.ListPlaceAdapter;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Objects;

public class RefreshPlaceListener implements SwipeRefreshLayout.OnRefreshListener {
    private ListFragment listFragment;

    public RefreshPlaceListener(ListFragment listFragment) {
        this.listFragment = listFragment;
    }

    @Override
    public void onRefresh() {

        if (this.listFragment.displayMode.equals(ListFragment.DisplayMode.PLACE))
        ((ListPlaceAdapter) Objects.requireNonNull(this.listFragment.list.getAdapter())).updateItems(Objects.requireNonNull(AbstractDAOFactory.getFactory(listFragment.getActivity(), ValueHelper.INSTANCE.getFactoryType())).getPlaceDAO().findAllGroupByCategory());

        else if (this.listFragment.displayMode.equals(ListFragment.DisplayMode.CATEGORY))
            ((ListCategoryAdapter) Objects.requireNonNull(this.listFragment.list.getAdapter())).updateItems(Objects.requireNonNull(AbstractDAOFactory.getFactory(listFragment.getActivity(), ValueHelper.INSTANCE.getFactoryType())).getCategoryDAO().findAll());

        this.listFragment.layout.setRefreshing(false);
    }
}
