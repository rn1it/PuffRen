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
import com.rn1.puffren.PuffRenApplication
import com.rn1.puffren.R
import com.rn1.puffren.data.Day
import com.rn1.puffren.data.PartnerInfo
import com.rn1.puffren.databinding.FragmentLocationBinding
import com.rn1.puffren.ext.dismissDialog
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.loadingDialog
import com.rn1.puffren.ext.showDialog
import com.rn1.puffren.network.LoadApiStatus

class LocationFragment : Fragment() {

    lateinit var binding: FragmentLocationBinding
    val viewModel by viewModels<LocationViewModel> { getVmFactory() }
    private val makers: MutableList<Marker> = mutableListOf()

    private val loadingDialog by lazy { loadingDialog() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLocationBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when(it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
        initMap()
    }

    private fun setupTabLayout() {

        binding.tabLocationItem.apply {
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    removeAllMarkers()

                    when (tab.position) {
                        0 -> viewModel.getPartnersInfoByDay(Day.TODAY)
                        else -> viewModel.getPartnersInfoByDay(Day.TOMORROW)
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {}
                override fun onTabReselected(p0: TabLayout.Tab?) {}
            })
        }
    }

    private fun initMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private val callback = OnMapReadyCallback { googleMap ->

        val taiwan = LatLng(24.133118710492916, 121.13635709722656)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(taiwan, 7F))

        viewModel.partners.observe(viewLifecycleOwner, Observer {
            it?.let {

                for (partner in it) {
                    makers.add(
                        googleMap.addMarker(
                            partner.latLng?.let { latLng ->
                                MarkerOptions()
                                    .position(latLng)
                                    .title(partner.openLocation)
                                    .snippet(
                                        setMapDialogMessage(partner)
                                    )
                                    .icon(
                                        BitmapDescriptorFactory.fromBitmap(
                                            generateSmallIcon(partner.openStatus)
                                        )
                                    )
                            }
                        ))
                }
            }
        })

        googleMap.setOnInfoWindowClickListener {
            val gmmIntentUri: Uri = Uri.parse(
                "geo:${it.position.latitude},${it.position.longitude}?q=${Uri.encode(it.title)}"
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")

            startActivity(mapIntent)
        }
    }

    private fun generateSmallIcon(openStatus: Int?): Bitmap {
        val height = 100
        val width = 100
        return when (openStatus) {
            1 -> {
                val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.brown_marker)
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            }
            else -> {
                val bitmap = BitmapFactory.decodeResource(this.resources, R.drawable.red_marker)
                Bitmap.createScaledBitmap(bitmap, width, height, false)
            }
        }
    }

    private fun removeAllMarkers() {
        for (maker in makers) {
            maker.remove()
        }
    }

    private fun setMapDialogMessage(partner: PartnerInfo): String {

        return when(partner.openStatus) {
            0 -> {
                "預計營業時間: ${partner.expectedOpenTime} ${PuffRenApplication.instance.getString(
                    R.string.puffren_level,
                    partner.level ?: getString(R.string.no_level)
                )}"
            }
            else -> {
                PuffRenApplication.instance.getString(
                    R.string.puffren_level,
                    partner.level ?: getString(R.string.no_level)
                )
            }
        }
    }
}