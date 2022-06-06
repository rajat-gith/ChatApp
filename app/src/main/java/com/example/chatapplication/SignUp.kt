package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth=FirebaseAuth.getInstance()
        val btnlogin=findViewById<Button>(R.id.Loginbtn)

        btnlogin.setOnClickListener {
            val register_email=register_email.text.toString()
            val registerpassword=register_password.text.toString()
            if(checking()){
                mAuth.createUserWithEmailAndPassword(register_email,registerpassword)
                    .addOnCompleteListener(this){ task->
                        if(task.isSuccessful()){
                            Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
                            val intent= Intent(this,MainActivity::class.java)
                            startActivity(intent);
                        }else{
                            Toast.makeText(this,"Some Error Occured",Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this,"Empty Fields",Toast.LENGTH_SHORT).show()
            }
        }

    }
    private fun checking():Boolean{
        if(register_email.text.toString().trim { it<' ' }.isNotEmpty()
            && register_password.text.toString().trim { it<' ' }.isNotEmpty()
            && name.text.toString().trim { it<' ' }.toString().isNotEmpty())
        {
            return true
        }
        return false
    }
}