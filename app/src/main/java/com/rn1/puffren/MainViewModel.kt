package com.rn1.puffren

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rn1.puffren.data.User
import com.rn1.puffren.data.source.PuffRenRepository
import com.rn1.puffren.util.CurrentFragmentType
import com.rn1.puffren.util.Logger

class MainViewModel (private val puffRenRepository: PuffRenRepository) : ViewModel() {

//    private val _user = MutableLiveData<User>()
//    val user: LiveData<User>
//        get() = _user

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

    var user: User? = null

    fun initViewModel(){
        Logger.d("mainViewModel ready")
    }

    fun setupUser(user : User){
        this.user = user
    }
}