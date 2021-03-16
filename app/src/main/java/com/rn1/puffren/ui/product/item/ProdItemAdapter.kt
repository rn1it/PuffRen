package com.rn1.puffren.ui.product.item

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.Product
import com.rn1.puffren.databinding.ItemProductBinding

class ProdItemAdapter(val viewModel: ProdItemViewModel): ListAdapter<Product, ProdItemAdapter.ProductViewHolder>(ProductDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
        holder.itemView.setOnClickListener {
            viewModel.navigateToProductDetail(product)
        }
    }

    class ProductViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(product: Product){
            binding.product = product
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): ProductViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemProductBinding.inflate(inflater, parent, false)
                return ProductViewHolder(binding)
            }
        }

    }

    object ProductDiffCallbackUtil: DiffUtil.ItemCallback<Product>() {

        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }


}