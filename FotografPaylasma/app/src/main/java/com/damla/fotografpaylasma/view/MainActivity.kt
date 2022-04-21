package com.damla.fotografpaylasma.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.view.View
import com.damla.fotografpaylasma.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth :  FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val guncelKullanici = auth.currentUser
        if(guncelKullanici != null) {
            val intent = Intent(this, HaberlerActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun girisYap(view: View){
        auth.signInWithEmailAndPassword(emailText.text.toString(), passwordText.text.toString()).addOnCompleteListener{

            if(it.isSuccessful){
                val guncelKullanici = auth.currentUser?.email.toString()
                Toast.makeText(this, "Hoşgeldin ${guncelKullanici}", Toast.LENGTH_LONG).show()

                val intent = Intent(this, HaberlerActivity::class.java)
                startActivity(intent)
                finish()
            }
        }.addOnFailureListener {
            Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    fun kayitOl(view: View){

        val email = emailText.text.toString()
        val password = passwordText.text.toString()

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            //asenkron
            if(it.isSuccessful){
                //diğer aktiviye gidelim
                val intent = Intent(applicationContext, HaberlerActivity::class.java)
                startActivity(intent)
                finish()

            }

        }.addOnFailureListener{ exception ->
            //localizedMessage -> kullanıcının anlayacağı dilden mesaja çevrilir
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show()
        }

    }
}