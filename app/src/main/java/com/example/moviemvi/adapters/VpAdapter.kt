package com.example.moviemvi.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemvi.databinding.ItemSinglePagerBinding
import com.example.moviemvi.models.Search
import com.squareup.picasso.Picasso

class VpAdapter : RecyclerView.Adapter<VpAdapter.Vh>() {

    private val list = ArrayList<Search>()

    inner class Vh(private val binding: ItemSinglePagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun onBind(search: Search) {
            Picasso.get().load(search.Poster)
                .into(binding.contentImage)
            binding.tvDescription.text = "${search.Type}, year ${search.Year}"
//            val renderScriptProvider = RenderScriptProvider(binding.root.context)
//            binding.contentImage.clipToOutline = true
//            binding.imageCardShadow.blurScript = RenderScriptBlur(renderScriptProvider)
            binding.tvTitle.text = search.Title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(
            ItemSinglePagerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setContent(list_m: List<Search>) {
        list.clear()
        list.addAll(list_m)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}