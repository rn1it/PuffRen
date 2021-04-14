package com.rn1.puffren

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

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

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
//            .asBitmap()
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder))
            .into(imageView)
    }
}