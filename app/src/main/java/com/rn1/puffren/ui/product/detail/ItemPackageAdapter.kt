package com.rn1.puffren.ui.product.detail

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.component.SelectedSquare
import com.rn1.puffren.data.ItemPackage
import com.rn1.puffren.databinding.ItemPackageBinding

class ItemPackageAdapter(
    val viewModel: DetailViewModel
    ): ListAdapter<ItemPackage, ItemPackageAdapter.ItemPackageViewHolder>(ItemPackageDiffCallbackUtil) {

    var selectedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemPackageViewHolder {
        return ItemPackageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemPackageViewHolder, position: Int) {

        val itemPackage = getItem(position)

        holder.itemView.setOnClickListener {
            selectedPosition = position
            viewModel.setPackage(itemPackage)
            notifyDataSetChanged()
        }

        holder.bind(itemPackage, selectedPosition)
    }

    class ItemPackageViewHolder(private val binding: ItemPackageBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(itemPackage: ItemPackage, position: Int){
            binding.itemPackage = itemPackage

            if (position == adapterPosition) {
                binding.layoutPackage.background = SelectedSquare()
            } else {
                binding.layoutPackage.background = null
            }

            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): ItemPackageViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemPackageBinding.inflate(inflater, parent, false)
                return ItemPackageViewHolder(binding)
            }
        }
    }

    object ItemPackageDiffCallbackUtil: DiffUtil.ItemCallback<ItemPackage>() {

        override fun areItemsTheSame(oldItem: ItemPackage, newItem: ItemPackage): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: ItemPackage, newItem: ItemPackage): Boolean {
            return oldItem == newItem
        }

    }
}