package com.example.picviewkontlin

import AllIPhotosAdapter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.picviewkontlin.databinding.ActivityClickAlbumImagesBinding


class ClickAlbumPhotosActivity : AppCompatActivity() {
    private lateinit var folderName:String
    lateinit var binding : ActivityClickAlbumImagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClickAlbumImagesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        folderName= intent.getStringExtra("FolderName")?:"Favorites"


        val albumsImageRecyclerview: RecyclerView = findViewById(R.id.albumsImageRecyclerView)
        albumsImageRecyclerview.layoutManager = GridLayoutManager(this, 4)


        val albumsTextView: TextView = findViewById(R.id.albumsTextView)
        albumsTextView.text = folderName
        if (folderName != "Favorites") {

            val allPhotoList: ArrayList<Uri>? = intent.getParcelableArrayListExtra("allPhotoList")

            val allImagesAdapter = allPhotoList?.let { AllIPhotosAdapter(it, applicationContext) }
            albumsImageRecyclerview.adapter = allImagesAdapter

        } else {
            setFavoritesAdapter()
        }
    }

    private fun setFavoritesAdapter() {
        val databaseHandler = DatabaseHandler(applicationContext)
        val allPhotoList = databaseHandler.getAllImages()

        val allImagesAdapter = AllIPhotosAdapter(allPhotoList, applicationContext)
        binding.albumsImageRecyclerView.adapter = allImagesAdapter
    }

    override fun onResume() {
        super.onResume()
        if(folderName == "Favorites")
            setFavoritesAdapter()
    }

}


