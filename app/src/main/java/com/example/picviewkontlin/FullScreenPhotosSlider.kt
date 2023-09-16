package com.example.picviewkontlin

import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2


class FullScreenPhotosSlider : AppCompatActivity() {

    companion object {
        lateinit var allPhotoList: ArrayList<Uri>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image_slider)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        @Suppress("DEPRECATION")
        allPhotoList = intent.getParcelableArrayListExtra("allPhotoList")!!
        val currentPosition: Int = intent.getIntExtra("currentPosition", 0)

        val allPhotoViewPager: ViewPager2 = findViewById(R.id.allPhotoViewPager)

        val imageSliderAdapter = PhotoSliderAdapter(allPhotoList, applicationContext)
        allPhotoViewPager.adapter = imageSliderAdapter
        allPhotoViewPager.currentItem = currentPosition

    }
}