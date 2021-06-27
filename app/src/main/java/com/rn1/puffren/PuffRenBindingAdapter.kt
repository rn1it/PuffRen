package com.rn1.puffren

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rn1.puffren.data.Event
import com.rn1.puffren.util.Util.getString

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

/**
 * Displays currency price to [TextView] by [Int]
 */
@BindingAdapter("quantity")
fun bindQuantity(textView: TextView, quantity: Int?) {
    quantity?.let { textView.text = PuffRenApplication.instance.getString(R.string._quantity, it) }
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, imgUrl: String?){
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder))
            .into(imageView)
    }
}

/**
 * Displays member level to [TextView] by [Int]
 */
@BindingAdapter("level")
fun bindLevel(textView: TextView, level: String?) {
    level?.let {
        textView.text = when (it) {
            //"1" -> getString(R.string.lvl1_member)
            //"2" -> getString(R.string.lvl1_member)
            //"3" -> getString(R.string.lvl1_member)
            //"4" -> getString(R.string.lvl1_member)
            //"5" -> getString(R.string.lvl1_member)
            "" -> getString(R.string.not_evaluate_yet)
            else -> it
        }
    }
}

/**
 * Displays performance level to [TextView]
 */
@BindingAdapter("performanceLevel")
fun bindPerformanceLevel(textView: TextView, level: String?) {
    level?.let { textView.text = PuffRenApplication.instance.getString(R.string._level_test, it) }
}

/**
 * Displays performance score to [TextView]
 */
@BindingAdapter("score")
fun bindPerformanceScore(textView: TextView, score: Int?) {
    score?.let { textView.text = PuffRenApplication.instance.getString(R.string._score, it) }
}

/**
 * Displays date period [TextView]
 */
@BindingAdapter("period")
fun bindPeriod(textView: TextView, event: Event?) {
    event?.let {
        textView.text = PuffRenApplication.instance.getString(R.string.period_to_, it.startDate, it.endDate)
    }
}

/**
 * Displays button by enable or not
 */
@BindingAdapter("button_scratch_enable")
fun bindScratchButtonEnable(button: Button, hasRecord: Int?) {
    hasRecord?.let {
        button.apply {
            when(hasRecord) {
                0 -> {
                    isEnabled = true
                    backgroundTintList = ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.orange_ffa626))
                }
                else -> {
                    isEnabled = false
                    backgroundTintList = ColorStateList.valueOf(PuffRenApplication.instance.getColor(R.color.grey_e1e1e1))
                }
            }
        }
    }
}