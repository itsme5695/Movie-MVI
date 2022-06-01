package com.example.moviemvi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemvi.R
import com.example.moviemvi.databinding.ItemHomeRvBinding
import com.example.moviemvi.models.room_data_base.entity.MoveNewPlayingEntity
import com.squareup.picasso.Picasso

class AdapterHomeRv(var list: List<MoveNewPlayingEntity>, val itemClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<AdapterHomeRv.ViewHolder>() {

    inner class ViewHolder(val homeItemBinding: ItemHomeRvBinding) :
        RecyclerView.ViewHolder(homeItemBinding.root) {

        fun onBind(movePopularEntity: MoveNewPlayingEntity) {
            Picasso.get().load(movePopularEntity.image_url).error(R.drawable.not_load_image).into(homeItemBinding.image)
            homeItemBinding.apply {
                tagStateDescription.setText(movePopularEntity.description)
                moveTitle.setText(movePopularEntity.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemHomeRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list.get(position))

        holder.itemView.setOnClickListener {
            itemClickListener.invoke(list.get(position).id)
        }
        val animationFadeIn = AnimationUtils.loadAnimation(
            holder.homeItemBinding.root.context,
            R.anim.alpha
        )
        holder.itemView.startAnimation(animationFadeIn)
    }

    override fun getItemCount(): Int = list.size

}