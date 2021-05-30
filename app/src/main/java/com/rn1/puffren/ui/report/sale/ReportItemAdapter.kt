package com.rn1.puffren.ui.report.sale

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rn1.puffren.data.ReportItem
import com.rn1.puffren.databinding.ItemInputSaleItemBinding

class ReportItemAdapter(
    val viewModel: SaleReportViewModel
): ListAdapter<ReportItem, ReportItemAdapter.ReportItemViewHolder>(ReportItemDiffCallbackUtil) {

    lateinit var list: List<ReportItem>

    fun passAndSubmitList(list: List<ReportItem>) {
        this.list = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportItemViewHolder {
        return ReportItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ReportItemViewHolder, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    class ReportItemViewHolder(private val binding: ItemInputSaleItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(reportItem: ReportItem, viewModel: SaleReportViewModel){
            binding.reportItem = reportItem
            binding.textQuantity.addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {}
                override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.setItemAmount(reportItem.id, text.toString())
                }
            })

            binding.executePendingBindings()
        }

        companion object{

            fun from(parent: ViewGroup): ReportItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemInputSaleItemBinding.inflate(inflater, parent, false)
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