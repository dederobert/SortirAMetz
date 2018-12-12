package a1819.m2ihm.sortirametz.adapter

import a1819.m2ihm.sortirametz.R
import a1819.m2ihm.sortirametz.models.User
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class UserListAdapter(var context: Context?, var users: MutableList<User>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as UserListHolder).bind(users.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return UserListHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.card_user, parent, false))
    }
}