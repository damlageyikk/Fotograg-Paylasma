package com.damla.fotografpaylasma.view

import android.content.AbstractThreadedSyncAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.damla.fotografpaylasma.R
import com.damla.fotografpaylasma.adapter.HaberRecyclerAdapter
import com.damla.fotografpaylasma.model.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_paylasim.*

class HaberlerActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth
    private lateinit var  database : FirebaseFirestore
    private lateinit var recyclerViewAdapter: HaberRecyclerAdapter

    var postListesi = ArrayList<Post>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paylasim)

        auth = FirebaseAuth.getInstance()
        database = FirebaseFirestore.getInstance()


        verileriAl()

        var layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerViewAdapter = HaberRecyclerAdapter(postListesi)
        recyclerView.adapter = recyclerViewAdapter

    }

    fun verileriAl(){
        database.collection("Post").orderBy("tarih", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            }else {
                if(value != null) {
                    if(!value.isEmpty){
                        //boş değilse

                        val documents = value.documents
                        postListesi.clear()
                        for(document in documents){
                            val kullaniciEmail = document.get("kullaniciemail") as String
                            val kullaniciYorum = document.get("kullaniciyorum") as String
                            val gorselUrl = document.get("gorselurl") as String

                            val indirilenPost = Post(kullaniciEmail, kullaniciYorum, gorselUrl)
                            postListesi.add(indirilenPost)

                        }
                        recyclerViewAdapter.notifyDataSetChanged()

                    }
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.secenekler_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       //Hangi id tıklandıysa ona göre işlem yapar.

        if(item.itemId == R.id.fotografpaylas){
            //fotograf paylasma aktivitesine gidilecek
            val intent = Intent(this, FotografPaylasmaActivity::class.java)
            startActivity(intent)

        }else if(item.itemId == R.id.cikis_yap){
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        return super.onOptionsItemSelected(item)
    }
}