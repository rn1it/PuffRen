package com.rn1.puffren.ui.achievement

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rn1.puffren.data.Achievement
import com.rn1.puffren.data.DataResult
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.UserManager
import kotlinx.coroutines.launch

class AchievementViewModel(
    private val repository: PuffRenRepository,
    private val achievementTypeFilter: AchievementTypeFilter
): ViewModel() {

    private val _achievements = MutableLiveData<List<Achievement>>()
    val achievements: LiveData<List<Achievement>>
        get() = _achievements

    private val _status = MutableLiveData<LoadApiStatus>()
    val status: LiveData<LoadApiStatus>
        get() = _status

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getMemberAchievement(achievementTypeFilter)
    }

    private fun getMemberAchievement(achievementTypeFilter: AchievementTypeFilter) {

        viewModelScope.launch {

            _achievements.value = when(val result = repository.getMemberAchievement(UserManager.userToken!!)) {
                is DataResult.Success -> {
                    _status.value = LoadApiStatus.DONE
                    result.data.filter { it.rewardReceived == achievementTypeFilter.status }
                }
                is DataResult.Fail -> {
                    _error.value = result.error
                    null
                }
                is DataResult.Error -> {
                    _error.value = result.exception.toString()
                    null
                }
            }
        }
    }
}