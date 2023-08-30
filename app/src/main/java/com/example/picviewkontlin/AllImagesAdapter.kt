import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picviewkontlin.R


class AllImagesAdapter(private val allImageList: ArrayList<Uri>) : RecyclerView.Adapter<AllImagesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.all_images_raw, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageUri = allImageList[position]
        Glide.with(holder.itemView.context)
            .load(imageUri)
            .into(holder.itemView.findViewById(R.id.allPhotoImageView))
    }

    override fun getItemCount(): Int {
        return allImageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
