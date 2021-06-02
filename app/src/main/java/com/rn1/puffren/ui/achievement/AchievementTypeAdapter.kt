package com.rn1.puffren.ui.achievement

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class AchievementTypeAdapter(
    fragmentManager: FragmentManager
): FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount() = AchievementTypeFilter.values().size

    override fun getItem(position: Int): Fragment {
        return AchievementFragment(AchievementTypeFilter.values()[position])
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(val filter = AchievementTypeFilter.values()[position]){
            AchievementTypeFilter.UNCOMPLETED -> filter.value
            AchievementTypeFilter.COMPLETED -> filter.value
        }
    }
}