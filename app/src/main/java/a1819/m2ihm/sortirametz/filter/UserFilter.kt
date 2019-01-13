package a1819.m2ihm.sortirametz.filter

import a1819.m2ihm.sortirametz.adapter.UserListAdapter
import a1819.m2ihm.sortirametz.bdd.dao.UserDAO
import a1819.m2ihm.sortirametz.bdd.factory.AbstractDAOFactory
import a1819.m2ihm.sortirametz.helpers.ValueHelper
import a1819.m2ihm.sortirametz.models.User
import android.content.Context
import android.util.Log
import android.widget.Filter
import android.widget.Toast
import java.util.*

class UserFilter(var adapter: UserListAdapter, var context: Context?) : Filter() {

    override fun performFiltering(constraint: CharSequence?): FilterResults {
        val tmp = LinkedList<User>()
        AbstractDAOFactory.getFactory(context, ValueHelper.INSTANCE.factoryType)
                .userDAO.findAllOther().forEach {
            Log.d("[FILTER]", "user $it")
            if (it.username.contains(constraint!!, true)) {
                tmp.add(it)
            }
        }
        val res = FilterResults()
        res.values = tmp;
        return res
    }

    override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
        adapter.users = (results?.values as LinkedList<User>)
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
    }

}
