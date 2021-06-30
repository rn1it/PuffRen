package com.rn1.puffren.ui.event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.R
import com.rn1.puffren.data.Prize
import com.rn1.puffren.databinding.FragmentEventBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout
import dev.skymansandy.scratchcardlayout.util.ScratchCardUtils

class EventFragment : Fragment(), ScratchListener {

    lateinit var binding: FragmentEventBinding
    private val viewModel by viewModels<EventViewModel> { getVmFactory() }
    private lateinit var scratchCardView: View
    private var isWinPrize: Boolean = false
    private lateinit var prize: Prize

    private val loadingDialog by lazy { loadingDialog() }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentEventBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.buttonStartScratchCard.setOnClickListener {
            viewModel.getPrizeByEventId()
        }

        viewModel.prize.observe(viewLifecycleOwner, Observer {
            it?.let {
                prize = it
                getScratchCardDialog(it)
            }
        })

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

    override fun onResume() {
        super.onResume()
        viewModel.getEventInfo()
    }

    @SuppressLint("InflateParams")
    private fun getScratchCardDialog(prize: Prize) {

        val builder = AlertDialog.Builder(context)
        builder.setCancelable(true)
        scratchCardView = LayoutInflater.from(requireContext()).inflate(R.layout.component_scratch_card, null)

        val scratchCard = scratchCardView.findViewById<ScratchCardLayout>(R.id.scratchCard)
        scratchCard.setScratchListener(this)
        scratchCard.setRevealFullAtPercent(90)
        scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(requireContext(), 20F))

        builder.setView(scratchCardView)
        val alertDialog = builder.create()
        alertDialog.show()

        if (null == prize.prizeCouponId && null == prize.prizeCouponTitle) {
            isWinPrize = false
            scratchCardView.findViewById<ImageView>(R.id.image_scratch_background)
                .setImageResource(R.drawable.scratch_card_lose)
        } else {
            isWinPrize = true
            scratchCardView.findViewById<ImageView>(R.id.image_scratch_background)
                .setImageResource(R.drawable.scratch_card_win)
        }
    }

    override fun onScratchStarted() {
        Logger.d("Scratch started")
    }

    override fun onScratchProgress(scratchCardLayout: ScratchCardLayout, atLeastScratchedPercent: Int) {
        Logger.d("Progress = $atLeastScratchedPercent")
    }

    override fun onScratchComplete() {
        Logger.d("Scratch ended")
        scratchCardView.findViewById<TextView>(R.id.text_scratch_result_title).apply {
            show()
            text = when(isWinPrize) {
                true -> context.getString(R.string.win_prize)
                else -> context.getString(R.string.lose_prize)
            }
        }
        scratchCardView.findViewById<TextView>(R.id.text_scratch_result).apply {
            show()
            text = when(isWinPrize) {
                true -> prize.prizeCouponTitle
                else -> context.getString(R.string.lose_prize_note)
            }
        }
    }
}