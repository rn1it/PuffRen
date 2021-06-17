package com.rn1.puffren.ui.car.content

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.ReportItem
import com.rn1.puffren.databinding.ItemFoodCartSetBinding
import com.rn1.puffren.ui.car.FoodCartViewModel

class FoodCarSetAdapter(
    private val viewModel: FoodCartViewModel
): ListAdapter<ReportItem, FoodCarSetAdapter.FoodCarSetViewHolder>(FoodCartSetDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodCarSetViewHolder {
        return FoodCarSetViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FoodCarSetViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    class FoodCarSetViewHolder(private val binding: ItemFoodCartSetBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reportItem: ReportItem, viewModel: FoodCartViewModel){
            binding.reportItem = reportItem
            binding.imagePlus.setOnClickListener {
                viewModel.plusItem(reportItem)
            }
            binding.imageMinus.setOnClickListener {
                viewModel.minusItem(reportItem)
            }

            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): FoodCarSetViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemFoodCartSetBinding.inflate(inflater, parent, false)
                return FoodCarSetViewHolder(binding)
            }
        }
    }

    object FoodCartSetDiffCallbackUtil: DiffUtil.ItemCallback<ReportItem>() {

        override fun areItemsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
            return oldItem == newItem
        }
    }
}