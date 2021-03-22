package com.rn1.puffren.ui.location

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.rn1.puffren.R


class LocationFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(25.042613022341943, 121.56475417585145)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("AppWorks School")
                .snippet("#12 1234")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13f))

        googleMap.setOnInfoWindowClickListener {

            val gmmIntentUri: Uri = Uri.parse("geo:${it.position.latitude},${it.position.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}