package com.backbase.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.utils.Constants.IMAGE_BASE_URL
import com.backbase.assignment.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class ImageMoviesAdapter(var imageUlrs: List<String>) :
    RecyclerView.Adapter<ImageMoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.now_movie_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = imageUlrs.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageUlrs[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView

        fun bind(imageUrl: String) = with(itemView) {
            poster = itemView.findViewById(R.id.image_movie_poster)
            Glide.with(poster.context)
                .load(IMAGE_BASE_URL + imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(poster)

        }
    }

}