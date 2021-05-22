package com.rn1.puffren.ui.location

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.tabs.TabLayout
import com.rn1.puffren.R
import com.rn1.puffren.data.Day
import com.rn1.puffren.databinding.FragmentLocationBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.util.Logger

class LocationFragment : Fragment() {

    lateinit var binding: FragmentLocationBinding
    val viewModel by viewModels<LocationViewModel> { getVmFactory() }
    private val makers: MutableList<Marker> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabLayout()
        initMap()
    }

    private fun setupTabLayout(){

        binding.tabLocationItem.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    Logger.d("aaaaa onTabSelected p = ${tab.position}")

                    removeAllMarkers()

                    when(tab.position) {
                        0 -> viewModel.getPartnersInfoByDay(Day.TODAY)
                        else -> viewModel.getPartnersInfoByDay(Day.TOMORROW)
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabReselected(p0: TabLayout.Tab?) {}
            })
        }
    }

    private fun initMap(){
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->

        val school = LatLng(25.042613022341943, 121.56475417585145)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(school, 11f))

        /* for test
        marker = googleMap.addMarker(
            MarkerOptions()
                .position(school)
                .title("AppWorks School")
                .snippet("#12 1234")
                .icon(BitmapDescriptorFactory.fromBitmap(generateSmallIcon(R.drawable.brown_marker)))
        ).apply {
            // isVisible = false
            // maybe can use tag to hide and show markers
            tag = 0
        }
        */

        viewModel.partners.observe(viewLifecycleOwner, Observer {
            it?.let {

                for (partner in it){
                    makers.add(
                        googleMap.addMarker(
                            partner.latLng?.let { latLng ->
                                MarkerOptions()
                                    .position(latLng)
                                    .title(partner.open_location)
                                    .snippet("#12 1234")
                                    .icon(BitmapDescriptorFactory.fromBitmap(generateSmallIcon(R.drawable.brown_marker)))
                            }
                        ))
                }
            }
        })

        googleMap.setOnInfoWindowClickListener {

            val gmmIntentUri: Uri = Uri.parse("geo:${it.position.latitude},${it.position.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }

    }

    private fun generateSmallIcon(id: Int): Bitmap {
        val height = 100
        val width = 100
        val bitmap = BitmapFactory.decodeResource(this.resources, id)
        return Bitmap.createScaledBitmap(bitmap, width, height, false)
    }

    private fun removeAllMarkers(){
        for(maker in makers) {
            maker.remove()
        }
    }
}