package com.rn1.puffren.util

import android.content.Context
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.gms.maps.model.LatLng
import com.rn1.puffren.PuffRenApplication
import java.lang.Exception
import java.util.*

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

    /**
     * Transfer Address to Latitude and Longitude
     */
    fun transferToLatLng(lat: Double? , lng: Double?, address: String?): LatLng?{
        var result: LatLng? = null

        lat?.let {
            lng?.let {
                result = LatLng(lat, lng)
                return result
            }
        }

        val geocoder = Geocoder(PuffRenApplication.instance, Locale.TAIWAN)

        try {
            val adder = geocoder.getFromLocationName(address,1)[0]
            result = LatLng(adder.latitude, adder.longitude)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }
}