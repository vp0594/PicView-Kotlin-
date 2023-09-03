package com.example.picviewkontlin


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomappbar.BottomAppBar


class PhotoSliderAdapter(private val allImageList: ArrayList<Uri>, private val context: Context) :
    RecyclerView.Adapter<PhotoSliderAdapter.ViewHolder>() {

    var visi: Int = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.image_silder, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return allImageList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoPath: Uri = allImageList[position]
        val slideImageView: ImageView = holder.itemView.findViewById(R.id.sliderImageView)
        Glide.with(context).load(photoPath).into(slideImageView)


        val bottomMenu: BottomAppBar = holder.itemView.findViewById(R.id.bottomMenu)

        bottomMenu.visibility = visi

        slideImageView.setOnClickListener {
            if (bottomMenu.visibility == View.GONE) {
                bottomMenu.visibility = View.VISIBLE
                visi = bottomMenu.visibility
                //here i want back notification bar


            } else {
                bottomMenu.visibility = View.GONE
                //here i want to hide notification bar

                visi = bottomMenu.visibility
            }
        }

        slideImageView.isClickable = false;

        val gestureDetector = GestureDetector(context, GestureTap(slideImageView))
        slideImageView.setOnTouchListener { v, event -> // let the gestureDetector to check and handle if there is a tap gesture
            gestureDetector.onTouchEvent(event)
            // must return false so that the view's onTouchEvent() can receive the event and handle zoom gestures.
            false
        }

        bottomMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.share -> {
                    val mimeType = getImageMimeType(context, photoPath)

                    if (mimeType != null) {
                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = mimeType

                        intent.putExtra(Intent.EXTRA_STREAM, photoPath)
                        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

                        val activityContext = holder.itemView.context as? Activity
                        activityContext?.startActivity(Intent.createChooser(intent, "Share Via"))
                    } else {
                        Toast.makeText(
                            context,
                            "Failed to determine image type",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    true
                }

                R.id.fav -> {
                    // Handle the favorite action
                    Toast.makeText(context, "Favorite clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.delete -> {
                    // Handle the delete action
                    Toast.makeText(context, "Delete clicked", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

//        slideImageView.setOnClickListener {
//            if (bottomMenu.visibility == View.GONE) {
//                bottomMenu.visibility = View.VISIBLE
//                visi = bottomMenu.visibility
//                //here i want back notification bar
//
//
//            } else {
//                bottomMenu.visibility = View.GONE
//                //here i want to hide notification bar
//
//                visi = bottomMenu.visibility
//            }
//        }
    }

    private fun getImageMimeType(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        return contentResolver.getType(uri)
    }

    class GestureTap(var view: View) : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            view.performClick()
            return true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}


