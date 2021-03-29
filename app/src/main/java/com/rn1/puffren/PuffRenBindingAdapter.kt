package com.rn1.puffren

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter

@SuppressLint("SetTextI18n")
@BindingAdapter("itemPackages")
fun setPackageString(textView: TextView, item: Int){
    textView.let {
        when (item) {
            1 -> {
                it.text = PuffRenApplication.instance.getString(R.string.buy_one)
            }
            else -> {
                it.text = PuffRenApplication.instance.getString(R.string._buy_more_than_one, item)
            }
        }
    }
}

/**
 * Displays currency price to [TextView] by [Int]
 */
@BindingAdapter("price")
fun bindPrice(textView: TextView, price: Int?) {
    price?.let { textView.text = PuffRenApplication.instance.getString(R.string.nt_dollars_, it) }
}