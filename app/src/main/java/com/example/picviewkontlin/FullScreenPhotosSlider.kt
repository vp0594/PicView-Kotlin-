package com.example.picviewkontlin

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.viewpager2.widget.ViewPager2


class FullScreenPhotosSlider : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_screen_image_slider)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )


        val allPhotoList: ArrayList<Uri>? = intent.getParcelableArrayListExtra("allPhotoList")
        val currentPosition: Int = intent.getIntExtra("currentPosition", 0)

        val allPhotoViewPager: ViewPager2 = findViewById(R.id.allPhotoViewPager)

        val imageSliderAdapter = allPhotoList?.let { PhotoSliderAdapter(it, applicationContext) }
        allPhotoViewPager.adapter = imageSliderAdapter
        allPhotoViewPager.currentItem = currentPosition

    }
}