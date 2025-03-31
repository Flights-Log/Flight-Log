package bitc.fullstack.FlightLog.flightmain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import bitc.fullstack.FlightLog.R
import com.bumptech.glide.Glide

class ImageAdapter(private val images: List<Int>) :
  RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

  inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.imageView)

    fun bind(imageRes: Int) {
      Glide.with(itemView.context)
        .load(imageRes)
        .into(imageView)
    }
  }

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ImageViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)

    view.layoutParams = ViewGroup.LayoutParams(
      ViewGroup.LayoutParams.MATCH_PARENT,
      ViewGroup.LayoutParams.MATCH_PARENT
    )
    return ImageViewHolder(view)
  }

  override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
    return holder.bind(images[position])
  }

  override fun getItemCount(): Int {
    return images.size
  }
}