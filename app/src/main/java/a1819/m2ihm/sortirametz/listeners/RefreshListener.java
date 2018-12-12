package a1819.m2ihm.sortirametz.listeners;

import a1819.m2ihm.sortirametz.ConsultFragment;
import a1819.m2ihm.sortirametz.adapter.ListAdapter;
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory;
import a1819.m2ihm.sortirametz.helpers.ValueHelper;
import android.support.v4.widget.SwipeRefreshLayout;

import java.util.Objects;

public class RefreshListener implements SwipeRefreshLayout.OnRefreshListener {
    private ConsultFragment consultFragment;

    public RefreshListener(ConsultFragment consultFragment) {
        this.consultFragment = consultFragment;
    }

    @Override
    public void onRefresh() {
        ((ListAdapter) Objects.requireNonNull(this.consultFragment.list.getAdapter()))
                .updateItems(
                        Objects.requireNonNull(AbstractDAOFactory.getFactory(consultFragment.getActivity(), ValueHelper.INSTANCE.getFactoryType())).getPlaceDAO()
                                .findAllGroupByCategory()
                        );
        this.consultFragment.layout.setRefreshing(false);
    }
}
