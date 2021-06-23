package com.rn1.puffren.ui.achievement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.data.Achievement
import com.rn1.puffren.databinding.FragmentAchievementBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus

class AchievementFragment(achievementTypeFilter: AchievementTypeFilter) : Fragment() {

    private lateinit var binding: FragmentAchievementBinding
    private val viewModel by viewModels<AchievementViewModel> { getVmFactory(achievementTypeFilter) }

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAchievementBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        val recyclerView = binding.recyclerAchievement
        val adapter = AchievementAdapter()
        recyclerView.adapter = adapter

        val ac1 = Achievement("1", "111", "299", 20, "2021-05-31", "2021-06-30", "好棒", 2, 7, 0)
        val ac2 = Achievement("2", "333", "288", 10, "2021-05-31", "2021-06-30", "好棒棒棒", 2, 7, 0)
        val ac3 = Achievement("3", "222", "266", 50, "2021-05-31", "2021-06-30", "好棒棒", 2, 7, 0)

        adapter.submitList(listOf(ac1, ac2, ac3))
//        viewModel.achievements.observe(viewLifecycleOwner, Observer {
//            it?.let {
//                if (it.isEmpty()) recyclerView.hide()
//                adapter.submitList(it)
//            }
//        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }
}