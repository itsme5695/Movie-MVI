package com.example.moviemvi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemvi.databinding.ItemMovieBinding
import com.example.moviemvi.repository.room.entity.Movie
import com.squareup.picasso.Picasso

class SAdapter(private val listener: OnClickListener) :
    ListAdapter<Movie, SAdapter.Vh>(DifferUtil) {

    interface OnClickListener {
        fun onItemClick(movie: Movie)
    }

    inner class Vh(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(movie: Movie) {
            Picasso.get().load(movie.poster).into(binding.contentImage)
            binding.contentTitle.text = movie.title
            binding.contentDescription.text = "${movie.ratings}, year ${movie.language}"
            itemView.setOnClickListener {
                listener.onItemClick(movie)
            }
        }
    }

    companion object DifferUtil : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return newItem.imdbId == oldItem.imdbId
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position))
    }
}