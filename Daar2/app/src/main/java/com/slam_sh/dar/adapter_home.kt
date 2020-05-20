package com.slam_sh.dar

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.slam_sh.dar.API.URL
import com.slam_sh.dar.data_home.NewsAndEvent
import kotlinx.android.synthetic.main.home_item.view.*
import java.net.URL
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule



 class adapter_home(var data: List<NewsAndEvent>?) :RecyclerView.Adapter<adapter_home.ViewHolderIndex>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapter_home.ViewHolderIndex {
        var myViewInflater=LayoutInflater.from(parent.context).inflate(R.layout.home_item,parent,false)
        return ViewHolderIndex(myViewInflater)

    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    override fun onBindViewHolder(holder: adapter_home.ViewHolderIndex, position: Int) {
        val data= data!![position]
        holder.bind(data)



    }

   inner class ViewHolderIndex(itemView:View):RecyclerView.ViewHolder(itemView) {
       fun bind(mydata:NewsAndEvent){
           var Title:TextView= itemView.title
           Title.text=mydata.title
           val Title2=itemView.title2.text
           val Title3=itemView.title3.text
            var image:ImageView=itemView.image_res

           Glide.with(itemView.context).load(mydata.iamge).into(image)

           }





}

}
































