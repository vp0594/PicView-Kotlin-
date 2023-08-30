package com.example.picviewkontlin

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AlbumsFragment : Fragment(), AlbumAdapter.AlbumClickListener {

    private lateinit var albumsRecyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private val albumsList = ArrayList<AlbumData>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_albums, container, false)
        val context: Context? = activity?.applicationContext

        albumsRecyclerView = view.findViewById(R.id.albumsRecyclerView)
        albumsRecyclerView.layoutManager = GridLayoutManager(context, 2)

        fetchAlbumsData()

        albumAdapter = AlbumAdapter(requireContext(), albumsList, this)
        albumsRecyclerView.adapter = albumAdapter

        return view
    }

    override fun onAlbumClick(folderName: String, imagePath: String) {

        val intent = Intent(requireContext(), ClickAlbumPhotosActivity::class.java)
        val allImageList = fetchImagesList(folderName)
        intent.putParcelableArrayListExtra("allPhotoList", allImageList)
        startActivity(intent)
    }

    private fun fetchImagesList(folderName: String): ArrayList<Uri> {
        val imagePaths = arrayListOf<Uri>()

        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(folderName)

        val cursor =  context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            selection,
            selectionArgs,
            null
        )

        cursor?.use {
            val imagePathColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val imagePath = it.getString(imagePathColumn)
                imagePaths.add(Uri.parse("file://$imagePath"))
            }
        }

        return imagePaths
    }

    private fun fetchAlbumsData() {
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME, // Folder name
            MediaStore.Images.Media.DATA // Image path
        )

        val sortOrder = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} ASC"

        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        val albumMap = mutableMapOf<String, String>()

        cursor?.use {
            val folderNameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val imagePathColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)

            while (it.moveToNext()) {
                val folderName = it.getString(folderNameColumn)
                val imagePath = it.getString(imagePathColumn)

                // Store the first image path encountered for each folder
                if (!albumMap.containsKey(folderName)) {
                    albumMap[folderName] = imagePath
                }
            }
        }

        albumMap.forEach { (folderName, imagePath) ->
            albumsList.add(AlbumData(folderName, imagePath))
        }
    }

}
