package com.rn1.puffren.ui.achievement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
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

        viewModel.achievements.observe(viewLifecycleOwner, Observer {
            it?.let {
                recyclerView.showOrHide(it.isEmpty())
                adapter.submitList(it)
            }
        })

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