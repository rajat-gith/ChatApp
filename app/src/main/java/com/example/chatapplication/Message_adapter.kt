package com.example.chatapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.receive.view.*
import kotlinx.android.synthetic.main.sent.view.*

class Message_adapter(val context:Context,val messagelist:ArrayList<Message>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE=1;
    val ITEM_SENT=2;


    override fun getItemViewType(position: Int): Int {
        val currentmessage=messagelist[position]
        if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentmessage.senderId)){
            return ITEM_SENT
        }
        else{
            return ITEM_RECEIVE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType==1){
            val itemView= LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            return ReceiveViewHolder(itemView)
        }else{
            val itemView= LayoutInflater.from(context).inflate(R.layout.sent,parent,false)
            return SentViewHolder(itemView)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentmessage=messagelist[position]
        if(holder.javaClass==SentViewHolder::class.java){

            val viewHolder=holder as SentViewHolder
            holder.sentmessage.text=currentmessage.message
        }else{
            val viewHolder=holder as ReceiveViewHolder
            holder.receivemessage.text=currentmessage.message
        }
    }

    override fun getItemCount(): Int {
        return messagelist.size
    }


    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val sentmessage=itemView.sent_message
    }
    class ReceiveViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val receivemessage=itemView.receive_message
    }
}