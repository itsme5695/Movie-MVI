package com.example.moviemvi.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemvi.databinding.ItemMovieBinding
import com.example.moviemvi.models.Search
import com.squareup.picasso.Picasso

class PagerAdapter(val listener: OnClickListener) :
    PagingDataAdapter<Search, PagerAdapter.Vh>(DifferUtil) {

    interface OnClickListener {
        fun onItemClick(search: Search)
    }


    inner class Vh(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(Search: Search) {
            Picasso.get().load(Search.Poster).into(binding.contentImage)
            binding.contentTitle.text = Search.Title
            itemView.setOnClickListener {
                listener.onItemClick(search = Search)
            }
        }
    }

    companion object DifferUtil : DiffUtil.ItemCallback<Search>() {
        override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
            return newItem == oldItem
        }

        override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
            return newItem.imdbID == oldItem.imdbID
        }

    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

}