package com.rn1.puffren.ui.achievement

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.Achievement
import com.rn1.puffren.databinding.ItemAchievementBinding

class AchievementAdapter: ListAdapter<Achievement, AchievementAdapter.CouponViewHolder>(CouponDiffCallbackUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        return CouponViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CouponViewHolder(private val binding: ItemAchievementBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(achievement: Achievement){
            binding.achievement = achievement
            binding.progressBar.progressTintList = ColorStateList.valueOf(
                PuffRenApplication.instance.getColor(R.color.orange_ffa626)
            )
            binding.textDate.text = PuffRenApplication.instance.getString(R.string.period_to_, achievement.startDate, achievement.endDate)
            binding.textProgress.text = PuffRenApplication.instance.getString(R.string.progress_divide_, achievement.progress, achievement.goal)
            binding.progressBar.max = achievement.goal!!
            binding.progressBar.progress = achievement.progress!!
            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): CouponViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemAchievementBinding.inflate(inflater, parent, false)
                return CouponViewHolder(binding)
            }
        }
    }

    object CouponDiffCallbackUtil: DiffUtil.ItemCallback<Achievement>() {

        override fun areItemsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem === newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Achievement, newItem: Achievement): Boolean {
            return oldItem == newItem
        }

    }
}