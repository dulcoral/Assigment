package com.backbase.assignment.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.backbase.assignment.R
import com.backbase.assignment.repository.models.GenreModel

class GenreMoviesAdapter(var genres: List<GenreModel>) :
    RecyclerView.Adapter<GenreMoviesAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movie_genre_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = genres.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var genreView: TextView

        fun bind(genre: GenreModel) = with(itemView) {
            genreView = itemView.findViewById(R.id.genre_title)
            genreView.text = genre.name
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

}