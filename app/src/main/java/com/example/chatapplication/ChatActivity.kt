package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    private lateinit var chat_recyclerview:RecyclerView
    private lateinit var messageAdapter: Message_adapter
    private lateinit var messagelist:ArrayList<Message>
    private lateinit var mDbref:DatabaseReference

    var receiverroom:String?=null
    var senderroom:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiverUid=intent.getStringExtra("uid")
        val senderUid=FirebaseAuth.getInstance().currentUser?.uid
        mDbref=FirebaseDatabase.getInstance().getReference()


        senderroom=receiverUid+senderUid
        receiverroom=senderUid+receiverUid

        supportActionBar?.title=name
        messagelist= ArrayList()

        chat_recyclerview=findViewById(R.id.chat_recyclerView)
        messageAdapter= Message_adapter(this,messagelist)

        chat_recyclerview.layoutManager=LinearLayoutManager(this)
        chat_recyclerview.adapter=messageAdapter


        mDbref.child("chat").child(senderroom!!).child("messages")
            .addValueEventListener(object:ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for(postsnapshot in snapshot.children){
                        val message=postsnapshot.getValue(Message::class.java)
                        messagelist.add(message!!)

                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })



        sent_button.setOnClickListener {
            val message=message_box.text.toString()
            val messageobject=Message(message,senderUid)

            mDbref.child("chat").child(senderroom!!).child("messages").push()
                .setValue(messageobject).addOnSuccessListener {
                    mDbref.child("chat").child(receiverroom!!).child("messages").push()
                        .setValue(messageobject)
                }
            message_box.setText("")

        }

    }
}