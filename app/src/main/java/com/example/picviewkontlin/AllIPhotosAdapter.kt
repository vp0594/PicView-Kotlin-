import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.picviewkontlin.FullScreenPhotosSlider
import com.example.picviewkontlin.R


class AllIPhotosAdapter(private val allImageList: ArrayList<Uri>, private val context: Context) :
    RecyclerView.Adapter<AllIPhotosAdapter.ViewHolder>() {

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

        holder.itemView.setOnClickListener {
            val intent = Intent(context, FullScreenPhotosSlider::class.java)
            intent.putParcelableArrayListExtra("allPhotoList", allImageList)
            intent.putExtra("currentPosition", position)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return allImageList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
