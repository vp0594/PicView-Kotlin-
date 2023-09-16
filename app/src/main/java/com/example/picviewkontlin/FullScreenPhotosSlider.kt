package com.example.picviewkontlin

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.picviewkontlin.databinding.ActivityFullScreenImageSliderBinding


class FullScreenPhotosSlider : AppCompatActivity() {
    private lateinit var databaseHandler:DatabaseHandler
    private var currentPosition = 0
    lateinit var binding:ActivityFullScreenImageSliderBinding

    companion object {
        lateinit var allPhotoList: ArrayList<Uri>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageSliderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

        databaseHandler = DatabaseHandler(this)
        @Suppress("DEPRECATION")
        allPhotoList = intent.getParcelableArrayListExtra("allPhotoList")!!
        currentPosition= intent.getIntExtra("currentPosition", 0)

        val allPhotoViewPager: ViewPager2 = findViewById(R.id.allPhotoViewPager)

        val imageSliderAdapter = PhotoSliderAdapter(allPhotoList, applicationContext)
        allPhotoViewPager.adapter = imageSliderAdapter
        allPhotoViewPager.setCurrentItem(currentPosition,false)

        allPhotoViewPager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                checkImageInFavorites(position)
            }
        })


    }

    override fun onStart() {
        super.onStart()
        checkImageInFavorites(currentPosition)
        ActionFragment.binding.favoritesBtn.setOnClickListener {
            if (databaseHandler.isImageInDatabase(allPhotoList[binding.allPhotoViewPager.currentItem])) {
                databaseHandler.deleteImageByUri(allPhotoList[binding.allPhotoViewPager.currentItem])
                ActionFragment.binding.favoritesBtn.setImageResource(R.drawable.ic_favorite_border)
            } else {
                databaseHandler.addImage(ImageItem(allPhotoList[binding.allPhotoViewPager.currentItem]))
                ActionFragment.binding.favoritesBtn.setImageResource(R.drawable.ic_favorite_filled)
            }
        }
    }

    fun checkImageInFavorites(position: Int) {
        if (databaseHandler.isImageInDatabase(allPhotoList[position])) {
            ActionFragment.binding.favoritesBtn.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            ActionFragment.binding.favoritesBtn.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}