package com.rn1.puffren.ui.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.databinding.ItemFavorBinding

class FavorAdapter : ListAdapter<String, FavorAdapter.FavorViewHolder>(FavorDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavorViewHolder {
        return FavorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class FavorViewHolder(private val binding: ItemFavorBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(favor: String){
            binding.textItemFavor.text = favor
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): FavorViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemFavorBinding.inflate(inflater, parent, false)
                return FavorViewHolder(binding)
            }
        }
    }

    object FavorDiffCallbackUtil: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}