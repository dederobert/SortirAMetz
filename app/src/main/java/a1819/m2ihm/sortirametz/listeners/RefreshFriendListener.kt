package a1819.m2ihm.sortirametz.listeners

import a1819.m2ihm.sortirametz.FriendFragment
import a1819.m2ihm.sortirametz.adapter.UserListAdapter
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory
import a1819.m2ihm.sortirametz.helpers.Logger
import a1819.m2ihm.sortirametz.helpers.ValueHelper
import android.support.v4.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_friends.*

class RefreshFriendListener(var friendFragment: FriendFragment) : SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        val adapter = (this.friendFragment.rcv_friends_list.adapter as UserListAdapter)
        adapter.users = AbstractDAOFactory.getFactory(friendFragment.context, ValueHelper.INSTANCE.factoryType).userDAO.findAllFriend(Logger.INSTANCE.user);
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        this.friendFragment.swp_friends_list.isRefreshing = false
        this.friendFragment.closeSearchMode()
    }
}