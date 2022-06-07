package com.example.chatapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_layout.view.*

class UserAdapter(val context: Context,var userList:ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView=LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentuser:User=userList[position]
        holder.txtname.text=currentuser.name
        holder.itemView.setOnClickListener {
            val intent=Intent(context,ChatActivity::class.java)
            intent.putExtra("name",currentuser.name)
            intent.putExtra("uid",currentuser.uid)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return userList.size
    }

    class UserViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val txtname=itemView.name1
    }
}