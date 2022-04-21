package com.damla.fotografpaylasma.adapter

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.damla.fotografpaylasma.R
import com.damla.fotografpaylasma.model.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.recycler_row.view.*

class HaberRecyclerAdapter(val postList : ArrayList<Post>) : RecyclerView.Adapter<HaberRecyclerAdapter.PostHolder>() {

    class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostHolder {
        //Inflater kullanarak oluşturmuş olduğumuz Recyler row bağlıyoruz
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_row, parent, false)
        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        holder.itemView.recycler_row_kullanici_email.text = postList[position].kullaniciEmail
        holder.itemView.recycler_row_yorum_text.text = postList[position].kullaniciYorum
        Picasso.get().load(postList[position].gorselUrl).into(holder.itemView.imageView)
    }

    override fun getItemCount(): Int {  //kaç tane eleman olacak
        return postList.size

    }
}