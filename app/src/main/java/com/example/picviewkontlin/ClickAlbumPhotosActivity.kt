package com.example.picviewkontlin

import AllIPhotosAdapter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ClickAlbumPhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_album_images)

        var albumsTextView: TextView = findViewById(R.id.albumsTextView)
        albumsTextView.text = intent.getStringExtra("FolderName")

        var albumsImageRecyclerview: RecyclerView = findViewById(R.id.albumsImageRecyclerView)
        albumsImageRecyclerview.layoutManager = GridLayoutManager(this, 4)

        val allPhotoList: ArrayList<Uri>? = intent.getParcelableArrayListExtra("allPhotoList")

        val allImagesAdapter = allPhotoList?.let { AllIPhotosAdapter(it, applicationContext) }
        albumsImageRecyclerview.adapter = allImagesAdapter
    }
}


