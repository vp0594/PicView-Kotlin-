package com.example.picviewkontlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AlbumAdapter(
    private val context: Context,
    private val albumsList: ArrayList<AlbumData>,
    private val albumClickListener: AlbumClickListener
) : RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    interface AlbumClickListener {
        fun onAlbumClick(folderName: String, imagePath: String)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.albums_raw, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return albumsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val albumData = albumsList[position]

        Glide.with(context)
            .load(albumData.getImagePath())
            .into(holder.itemView.findViewById(R.id.folderImageView))


        holder.itemView.findViewById<TextView>(R.id.folderNameTextView).text =
            albumData.getFolderName()

        holder.itemView.setOnClickListener {
            albumClickListener.onAlbumClick(albumData.getFolderName(), albumData.getImagePath())
        }
    }
}
