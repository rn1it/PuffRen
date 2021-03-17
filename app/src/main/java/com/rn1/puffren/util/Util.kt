package com.rn1.puffren.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.rn1.puffren.PuffRenApplication

object Util {

    fun isInternetConnected(): Boolean {
        val cm = PuffRenApplication.instance
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    fun getString(resourceId: Int): String {
        return PuffRenApplication.instance.getString(resourceId)
    }
}