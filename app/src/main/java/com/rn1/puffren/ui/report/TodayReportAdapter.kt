package com.rn1.puffren.ui.report

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.ReportOpenStatus
import com.rn1.puffren.databinding.ItemReportBinding

class TodayReportAdapter: ListAdapter<ReportOpenStatus, TodayReportAdapter.ReportViewHolder>(ReportDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        return ReportViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ReportViewHolder(private val binding: ItemReportBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reportOpenStatus: ReportOpenStatus){
            binding.reportOpenStatus = reportOpenStatus
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): ReportViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemReportBinding.inflate(inflater, parent, false)
                return ReportViewHolder(binding)
            }
        }
    }

    object ReportDiffCallbackUtil: DiffUtil.ItemCallback<ReportOpenStatus>() {

        override fun areItemsTheSame(oldItem: ReportOpenStatus, newItem: ReportOpenStatus): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ReportOpenStatus, newItem: ReportOpenStatus): Boolean {
            return oldItem == newItem
        }

    }
}