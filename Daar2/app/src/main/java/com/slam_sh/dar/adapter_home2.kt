package com.slam_sh.dar

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slam_sh.dar.data_home.RealEstate
import kotlinx.android.synthetic.main.home_item2.view.*

class adapter_home2(var data:List <RealEstate>):RecyclerView.Adapter<adapter_home2.ViewHolderIndex>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_home2.ViewHolderIndex {
        var myViewInflater=
            LayoutInflater.from(parent.context).inflate(R.layout.home_item2,parent,false)
        return adapter_home2.ViewHolderIndex(myViewInflater)
    }

    override fun getItemCount(): Int {
        return 9
    }

    override fun onBindViewHolder(holder: adapter_home2.ViewHolderIndex, position: Int) {
        val data= data[position]
        holder.bind(data)
    }
    class ViewHolderIndex(itemView: View):RecyclerView.ViewHolder(itemView) {
    @SuppressLint("SetTextI18n")
    fun bind(mydata:RealEstate){
        var Title: TextView? =itemView.title1
        Title?.text=mydata.title
        var Title1: TextView? =itemView.title2
        Title1?.text=mydata.description
        var Loce: TextView? =itemView.loce1
        Loce?.text=mydata.city
        var Loce2: TextView? =itemView.loce2
        Loce2?.text=mydata.municipality
        var Price: TextView? =itemView.price
        Price?.text=mydata.price

        var Image:ImageView?=itemView.image
        Glide.with(itemView.context).load(mydata.iamge).into(Image!!)
    }

    }
}