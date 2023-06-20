package com.ex.applicationxxx

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val chuckN: LiveData<List<LegoFort>>, val context: Context) : RecyclerView.Adapter<MyAdapter.ViewHolder> () {

   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val homeHeading = itemView.findViewById<TextView>(R.id.home_title)
       val homeImage = itemView.findViewById<ImageView>(R.id.home_image)
       val homeDetails = itemView.findViewById<TextView>(R.id.home_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.my_cardview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val home = chuckN.value?.get(position)

        val homeTitle = holder.homeHeading
        homeTitle.text = home?.created_at

        val homeImage = holder.homeImage
//        home?.url?.let {homeImage.setImageResource(it)}
        Glide.with(context)
            .load(home?.url)
            .into(homeImage)

        val homeDetails = holder.homeDetails
        homeDetails.text = home?.value
    }

    override fun getItemCount(): Int {
        return chuckN.value?.size as Int
    }
}