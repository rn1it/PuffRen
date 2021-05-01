package com.rn1.puffren

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.rn1.puffren.databinding.ActivityMainBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.util.CurrentFragmentType

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>{ getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = mainViewModel

        setupNavController()
        setupStatusBar()
    }

    private fun setupStatusBar(){
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = resources.getColor(R.color.orange_ffa626)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun setupNavController(){
        findNavController(R.id.navHostFragment).addOnDestinationChangedListener { navController: NavController, _: NavDestination, _: Bundle? ->
            mainViewModel.currentFragmentType.value = when (navController.currentDestination?.id) {
                R.id.homeFragment -> CurrentFragmentType.HOME
                R.id.prodFragment -> CurrentFragmentType.PRODUCT
                R.id.detailFragment -> CurrentFragmentType.DETAIL
                R.id.locationFragment -> CurrentFragmentType.LOCATION
                R.id.loginFragment -> CurrentFragmentType.LOGIN
                R.id.registryFragment -> CurrentFragmentType.REGISTRY
                R.id.profileFragment -> CurrentFragmentType.PROFILE
                else -> mainViewModel.currentFragmentType.value
            }
        }
    }
}

