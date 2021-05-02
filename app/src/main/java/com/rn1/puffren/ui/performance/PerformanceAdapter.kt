package com.rn1.puffren.ui.performance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.Performance
import com.rn1.puffren.databinding.ItemPerformanceBinding

class PerformanceAdapter: ListAdapter<Performance, PerformanceAdapter.PerformanceViewHolder>(PerformanceDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerformanceViewHolder {
        return PerformanceViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PerformanceViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PerformanceViewHolder(private val binding: ItemPerformanceBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(performance: Performance){
            binding.performance = performance
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): PerformanceViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPerformanceBinding.inflate(inflater, parent, false)
                return PerformanceViewHolder(binding)
            }
        }
    }

    object PerformanceDiffCallbackUtil: DiffUtil.ItemCallback<Performance>() {

        override fun areItemsTheSame(oldItem: Performance, newItem: Performance): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Performance, newItem: Performance): Boolean {
            return oldItem == newItem
        }

    }
}