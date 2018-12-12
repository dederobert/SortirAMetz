package a1819.m2ihm.sortirametz

import a1819.m2ihm.sortirametz.adapter.UserListAdapter
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory
import a1819.m2ihm.sortirametz.helpers.ValueHelper
import a1819.m2ihm.sortirametz.listeners.RefreshFriendListener
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendFragment: Fragment() {

    private lateinit var userDAO: UserDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_friends, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userDAO = AbstractDAOFactory.getFactory(context, ValueHelper.INSTANCE.factoryType).userDAO
        swp_friends_list.setOnRefreshListener(RefreshFriendListener(this))
        rcv_friends_list.layoutManager = LinearLayoutManager(context)
        rcv_friends_list.itemAnimator = DefaultItemAnimator()
        rcv_friends_list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        rcv_friends_list.adapter = UserListAdapter(context, userDAO.findAll())
    }
}