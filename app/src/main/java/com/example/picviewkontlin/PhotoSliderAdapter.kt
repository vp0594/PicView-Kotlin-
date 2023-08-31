package com.example.picviewkontlin

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView

class PhotoSliderAdapter(private val allImageList: ArrayList<Uri>, private val context: Context) :
    RecyclerView.Adapter<PhotoSliderAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_silder, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allImageList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoPath: Uri = allImageList[position]
        var slideImageView: ImageView = holder.itemView.findViewById(R.id.sliderImageView)
        Glide.with(context).load(photoPath).into(slideImageView)
        var bottomNavigationView: BottomNavigationView =
            holder.itemView.findViewById(R.id.bottomNavigationView)

        // IF I RUN THIS MY IMAGES WILL ZOOM IN AND OUT BUT IT WILL NOT SHOW MENU
//        holder.itemView.setOnClickListener {
//            bottomNavigationView.visibility =
//                if (bottomNavigationView.visibility == View.VISIBLE) View.GONE else View.VISIBLE
//        }

        // IF I RUN THIS IT WILL SHOW MENU BUT I CAN NO LONGER ZOOM IN OR OUT PHOTO
        slideImageView.setOnClickListener {  bottomNavigationView.visibility =
            if (bottomNavigationView.visibility == View.VISIBLE) View.GONE else View.VISIBLE }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}