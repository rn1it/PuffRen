package com.rn1.puffren.ui.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.OverdueInfo
import com.rn1.puffren.databinding.ItemOverdueBinding

class OverdueAdapter: ListAdapter<OverdueInfo, OverdueAdapter.ReportViewHolder>(ReportDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReportViewHolder(private val binding: ItemOverdueBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(data: OverdueInfo){
            with(binding) {
                overdueInfo = data
                recyclerOverdue.adapter = TodayReportAdapter().apply { submitList(data.details) }
                executePendingBindings()
            }
        }

        companion object{

            fun from(parent: ViewGroup): ReportViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemOverdueBinding.inflate(inflater, parent, false)
                return ReportViewHolder(binding)
            }
        }
    }

    object ReportDiffCallbackUtil: DiffUtil.ItemCallback<OverdueInfo>() {

        override fun areItemsTheSame(oldItem: OverdueInfo, newItem: OverdueInfo): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: OverdueInfo, newItem: OverdueInfo): Boolean {
            return oldItem == newItem
        }

    }
}