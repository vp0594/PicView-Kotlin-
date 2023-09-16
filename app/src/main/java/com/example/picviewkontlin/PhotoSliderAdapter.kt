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


class PhotoSliderAdapter(private val allImageList: ArrayList<Uri>, private val context: Context) :
    RecyclerView.Adapter<PhotoSliderAdapter.ViewHolder>() {

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

        slideImageView.setOnClickListener {
            if (ActionFragment.binding.root.visibility == View.INVISIBLE) {
                ActionFragment.binding.root.visibility = View.VISIBLE
                //here i want to show action bar
            } else {
                ActionFragment.binding.root.visibility = View.INVISIBLE
                //here i want to hide action bar
            }
        }

        //for sharing the image
        ActionFragment.binding.shareBtn.setOnClickListener {
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
        }

        slideImageView.isClickable = false

        val gestureDetector = GestureDetector(context, GestureTap(slideImageView))
        slideImageView.setOnTouchListener { _, event -> // let the gestureDetector to check and handle if there is a tap gesture
            gestureDetector.onTouchEvent(event)
            // must return false so that the view's onTouchEvent() can receive the event and handle zoom gestures.
            false
        }

        val favoriteAction = ActionFragment.binding.favoritesBtn
        val databaseHandler = DatabaseHandler(context)


        if (databaseHandler.isImageInDatabase(allImageList[position])) {
            favoriteAction.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteAction.setImageResource(R.drawable.ic_favorite_border)
        }

        favoriteAction.setOnClickListener {
            if (databaseHandler.isImageInDatabase(allImageList[position])) {
                databaseHandler.deleteImageByUri(allImageList[position])
                favoriteAction.setImageResource(R.drawable.ic_favorite_filled)

            } else {
                databaseHandler.addImage(ImageItem(allImageList[position]))
                favoriteAction.setImageResource(R.drawable.ic_favorite_border)
            }
        }

        ActionFragment.binding.deleteBtn.setOnClickListener {
            Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show()
        }

    }

    private fun getImageMimeType(context: Context, uri: Uri): String? {
        val contentResolver = context.contentResolver
        return contentResolver.getType(uri)
    }

    class GestureTap(private var view: View) : SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            view.performClick()
            return true
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}


