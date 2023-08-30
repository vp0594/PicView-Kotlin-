package com.example.picviewkontlin

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

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
        var photoPath: Uri = allImageList.get(position)
        Glide.with(context).load(photoPath).into(holder.itemView.findViewById(R.id.sliderImageView))
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}