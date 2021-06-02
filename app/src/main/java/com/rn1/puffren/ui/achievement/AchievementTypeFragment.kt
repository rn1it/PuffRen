package com.rn1.puffren.ui.achievement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.rn1.puffren.databinding.FragmentAchievementTypeBinding

class AchievementTypeFragment : Fragment() {

    lateinit var binding: FragmentAchievementTypeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAchievementTypeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewpagerAchievement.let {
            binding.tabsAchievement.setupWithViewPager(it)
            it.adapter = AchievementTypeAdapter(childFragmentManager)
            it.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabsAchievement))
        }

        return binding.root
    }
}