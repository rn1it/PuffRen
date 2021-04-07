package com.rn1.puffren.ui.product

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rn1.puffren.ui.product.item.ProdItemFragment

class ProdTypeAdapter(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = ProdTypeFilter.values().size

    override fun getItem(position: Int): Fragment {
        return ProdItemFragment(ProdTypeFilter.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(ProdTypeFilter.values()[position]){
            ProdTypeFilter.VENDOR -> "餐車商品"
            ProdTypeFilter.DELIVERY -> "宅配商品"
        }
    }
}