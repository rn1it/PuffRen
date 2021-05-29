package com.rn1.puffren.ui.history.item

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.ReportItem
import com.rn1.puffren.databinding.ItemReportSaleItemBinding

class ReportItemAdapter: ListAdapter<ReportItem, ReportItemAdapter.ReportItemViewHolder>(ReportItemDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportItemViewHolder {
        return ReportItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReportItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReportItemViewHolder(private val binding: ItemReportSaleItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reportItem: ReportItem){
            binding.reportItem = reportItem
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): ReportItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemReportSaleItemBinding.inflate(inflater, parent, false)
                return ReportItemViewHolder(binding)
            }
        }
    }

    object ReportItemDiffCallbackUtil: DiffUtil.ItemCallback<ReportItem>() {

        override fun areItemsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ReportItem, newItem: ReportItem): Boolean {
            return oldItem == newItem
        }

    }
}