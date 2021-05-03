package com.rn1.puffren.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
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

    /**
     * Initialize input manager in order to hide soft keyboard
     */
    fun hideKeyBoard(view: View) {
        val manager = PuffRenApplication.instance
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}