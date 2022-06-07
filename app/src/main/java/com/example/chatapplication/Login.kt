
package com.example.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.ActionCodeEmailInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*



class Login : AppCompatActivity() {
    private lateinit var mAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val SignupBtn=findViewById<Button>(R.id.SignUp)
        val LoginBtn=findViewById<Button>(R.id.Login)

        supportActionBar?.hide()
        mAuth= FirebaseAuth.getInstance()

        SignupBtn.setOnClickListener {
            val intent= Intent(this,com.example.chatapplication.SignUp::class.java)
            startActivity(intent)
        }

        LoginBtn.setOnClickListener {
            val email=email.text.toString()
            val password=password.text.toString()
            if(check()){
                mAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this){task->
                        if(task.isSuccessful) {
                            Toast.makeText(this, "LoginSuccess", Toast.LENGTH_SHORT).show()
                            val intent= Intent(this,MainActivity::class.java)
                            finish()
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

    private fun check():Boolean{
        if(email.text.toString().trim { it<=' ' }.isNotEmpty()
            && password.toString().trim{it<=' '}.isNotEmpty()){
            return true
        }
        return false
    }
}