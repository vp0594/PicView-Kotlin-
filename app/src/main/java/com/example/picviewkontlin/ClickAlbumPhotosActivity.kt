package com.example.picviewkontlin

import AllIPhotosAdapter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class ClickAlbumPhotosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_click_album_images)

        var FolderName: String? = intent.getStringExtra("FolderName")


        val albumsImageRecyclerview: RecyclerView = findViewById(R.id.albumsImageRecyclerView)
        albumsImageRecyclerview.layoutManager = GridLayoutManager(this, 4)


        if (FolderName != null) {
            val albumsTextView: TextView = findViewById(R.id.albumsTextView)
            albumsTextView.text = FolderName

            val allPhotoList: ArrayList<Uri>? = intent.getParcelableArrayListExtra("allPhotoList")

            val allImagesAdapter = allPhotoList?.let { AllIPhotosAdapter(it, applicationContext) }
            albumsImageRecyclerview.adapter = allImagesAdapter

        } else {
            val databaseHandler = DatabaseHandler(applicationContext)
            val allPhotoList = databaseHandler.getAllImages()

            val allImagesAdapter = AllIPhotosAdapter(allPhotoList, applicationContext)
            albumsImageRecyclerview.adapter = allImagesAdapter
        }
    }
}


