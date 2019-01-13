package a1819.m2ihm.sortirametz.adapter

import a1819.m2ihm.sortirametz.R
import a1819.m2ihm.sortirametz.models.User
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class UserListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val txt_friend_username:TextView = itemView.findViewById(R.id.txt_friend_username)
    val txt_friend_email:TextView = itemView.findViewById(R.id.txt_friend_email)
    val img_user_avatar:ImageView = itemView.findViewById(R.id.img_user_avatar)

    fun bind(user: User) {
        Picasso.get().load(Uri.parse(user.avatar)).into(img_user_avatar)
        this.txt_friend_username.text = user.username
        this.txt_friend_email.text = user.email
    }
}
