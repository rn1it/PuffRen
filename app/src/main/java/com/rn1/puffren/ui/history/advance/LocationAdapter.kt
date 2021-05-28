package com.rn1.puffren.ui.history.advance

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.databinding.ItemLocationBinding

class LocationAdapter(
    private val viewModel: AdvanceReportViewModel
    ) : ListAdapter<String, LocationAdapter.LocationViewHolder>(ReportDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = getItem(position)
        holder.bind(location)
        holder.itemView.setOnClickListener {
            viewModel.selectLocationOption(location)
        }
    }

    class LocationViewHolder(private val binding: ItemLocationBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(location: String){
            binding.textLocation.text = location
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): LocationViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemLocationBinding.inflate(inflater, parent, false)
                return LocationViewHolder(binding)
            }
        }
    }

    object ReportDiffCallbackUtil: DiffUtil.ItemCallback<String>() {

        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }
}