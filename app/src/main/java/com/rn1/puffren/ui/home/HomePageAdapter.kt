package com.rn1.puffren.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.HomePageItem
import com.rn1.puffren.databinding.ItemHomePageBinding


class HomePageAdapter(val viewModel: HomeViewModel) : ListAdapter<HomePageItem, HomePageAdapter.HomePageViewHolder>(HomepageItemDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePageViewHolder {
        return HomePageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: HomePageViewHolder, position: Int) {
        val homePageItem = getItem(position)
        holder.bind(homePageItem)
        holder.itemView.setOnClickListener {
            viewModel.navigate(homePageItem)
        }
    }

    class HomePageViewHolder(private val binding: ItemHomePageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(homePageItem: HomePageItem){
            binding.item = homePageItem
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): HomePageViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemHomePageBinding.inflate(inflater, parent, false)
                return HomePageViewHolder(binding)
            }
        }
    }

    object HomepageItemDiffCallbackUtil: DiffUtil.ItemCallback<HomePageItem>() {

        override fun areItemsTheSame(oldItem: HomePageItem, newItem: HomePageItem): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: HomePageItem, newItem: HomePageItem): Boolean {
            return oldItem == newItem
        }

    }

}