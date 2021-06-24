package com.backbase.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.utils.Constants.IMAGE_BASE_URL
import com.backbase.assignment.R
import com.backbase.assignment.repository.models.MovieModel
import com.backbase.assignment.ui.custom.RatingView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class MoviesAdapter(var items: MutableList<MovieModel>, var movieListener: MovieItemListener) :
    RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    fun addMoreItems(newItems: MutableList<MovieModel>) {
        val oldCount = itemCount
        items.addAll(newItems)
        this.notifyItemInserted(oldCount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.animation =
            AnimationUtils.loadAnimation(holder.itemView.context, R.anim.scale_anim)
        holder.itemView.setOnClickListener {
            movieListener.onItemClick(
                items[position].id,
                position
            )
        }
    }

    override fun getItemCount() = items.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var poster: ImageView
        lateinit var title: TextView
        lateinit var releaseDate: TextView
        lateinit var rating: RatingView

        fun bind(item: MovieModel) = with(itemView) {
            poster = itemView.findViewById(R.id.poster)
            Glide.with(poster.context)
                .load(IMAGE_BASE_URL + item.posterPath)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .into(poster)

            title = itemView.findViewById(R.id.title)
            title.text = item.title

            releaseDate = itemView.findViewById(R.id.releaseDate)
            releaseDate.text = item.releaseDate

            rating = itemView.findViewById(R.id.rating)
            rating.progress = (item.voteAverage * 10).toInt()
        }
    }

    interface MovieItemListener {
        fun onItemClick(movieId: Int, position: Int)
    }
}