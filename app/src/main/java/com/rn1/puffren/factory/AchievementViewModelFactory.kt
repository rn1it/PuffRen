package com.rn1.puffren.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.ui.achievement.AchievementTypeFilter
import com.rn1.puffren.ui.achievement.AchievementViewModel
import com.rn1.puffren.ui.product.ProdTypeFilter
import com.rn1.puffren.ui.product.item.ProdItemViewModel

@Suppress("UNCHECKED_CAST")
class AchievementViewModelFactory(
    private val puffRenRepository: PuffRenRepository,
    private val achievementTypeFilter: AchievementTypeFilter
): ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>)=
        with(modelClass) {
            when {
                isAssignableFrom(AchievementViewModel::class.java) ->
                    AchievementViewModel(puffRenRepository, achievementTypeFilter)
                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}