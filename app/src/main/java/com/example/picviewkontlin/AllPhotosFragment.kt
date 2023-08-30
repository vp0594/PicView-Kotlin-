package com.example.picviewkontlin

import AllImagesAdapter
import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AllPhotosFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_all_photos, container, false)
        val context: Context? = activity?.applicationContext
        val recyclerView = view.findViewById<RecyclerView>(R.id.allPhotoRecyclerView)
        recyclerView.isNestedScrollingEnabled = false

        val allPhotoRecyclerView: RecyclerView = view.findViewById(R.id.allPhotoRecyclerView)
        allPhotoRecyclerView.layoutManager = GridLayoutManager(context, 4)

        val imageList = fetchImagesList()

        val allImagesAdapter = context?.let { AllImagesAdapter(imageList, it) }
        allPhotoRecyclerView.adapter = allImagesAdapter
        return view
    }

    private fun fetchImagesList(): ArrayList<Uri> {
        val imageList = arrayListOf<Uri>()

        val projection = arrayOf(
            MediaStore.Images.Media._ID
        )

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        val query = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            projection,
            null,
            null,
            sortOrder
        )

        query?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val contentUri: Uri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    id
                )
                imageList.add(contentUri)
            }
        }

        return imageList
    }


}