package com.rn1.puffren.ui.coupon

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.Coupon
import com.rn1.puffren.databinding.ItemCouponBinding

class CouponAdapter: ListAdapter<Coupon, CouponAdapter.CouponViewHolder>(CouponDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        return CouponViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CouponViewHolder(private val binding: ItemCouponBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(coupon: Coupon){
            binding.coupon = coupon
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): CouponViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemCouponBinding.inflate(inflater, parent, false)
                return CouponViewHolder(binding)
            }
        }
    }

    object CouponDiffCallbackUtil: DiffUtil.ItemCallback<Coupon>() {

        override fun areItemsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Coupon, newItem: Coupon): Boolean {
            return oldItem == newItem
        }

    }
}