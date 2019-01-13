package a1819.m2ihm.sortirametz

import a1819.m2ihm.sortirametz.adapter.UserListAdapter
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory
import a1819.m2ihm.sortirametz.helpers.Logger
import a1819.m2ihm.sortirametz.helpers.ValueHelper
import a1819.m2ihm.sortirametz.listeners.RefreshFriendListener
import android.content.Context
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo.IME_ACTION_DONE
import android.view.inputmethod.InputMethodManager
import kotlinx.android.synthetic.main.fragment_friends.*

class FriendFragment: Fragment(), TextWatcher {

    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

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
        rcv_friends_list.adapter = UserListAdapter(context, userDAO.findAllFriend(Logger.INSTANCE.user))

        edt_search.addTextChangedListener(this)

        //Enter un search mode
        fab_add_friend?.setOnClickListener {
            openSearchMode()
        }

        btn_close_search?.setOnClickListener {
            closeSearchMode()
        }

        //Exist searchMode
        edt_search.setOnEditorActionListener {v, actionId, event ->
            if (actionId == IME_ACTION_DONE) {
                closeSearchMode()
            }
            false
        }
    }

    fun openSearchMode() {
        (fab_add_friend as View).visibility = GONE
        edt_search?.visibility = VISIBLE
        btn_close_search?.visibility = VISIBLE

        edt_search?.isFocusableInTouchMode = true
        edt_search?.requestFocusFromTouch()
        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(edt_search, 0)
        (rcv_friends_list.adapter as UserListAdapter).addingFriendMode = true;
        (rcv_friends_list.adapter as UserListAdapter).filter.filter("")
    }

    fun closeSearchMode() {
        edt_search.visibility = GONE
        btn_close_search.visibility = GONE
        (fab_add_friend as View).visibility = VISIBLE

        (activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(activity?.currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        val friends =  userDAO.findAllFriend(Logger.INSTANCE.user)
        (rcv_friends_list.adapter as UserListAdapter).users = friends
        (rcv_friends_list.adapter as UserListAdapter).addingFriendMode = false;
        (rcv_friends_list.adapter as UserListAdapter).notifyItemRangeChanged(0, friends.size)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        (rcv_friends_list.adapter as UserListAdapter).filter.filter(s)
    }
}