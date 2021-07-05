package com.rn1.puffren.ui.event

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.rn1.puffren.R
import com.rn1.puffren.data.EventInfo
import com.rn1.puffren.data.Prize
import com.rn1.puffren.databinding.FragmentEventBinding
import com.rn1.puffren.ext.*
import com.rn1.puffren.network.LoadApiStatus
import com.rn1.puffren.util.Logger
import com.rn1.puffren.util.Util.setTextToToast
import dev.skymansandy.scratchcardlayout.listener.ScratchListener
import dev.skymansandy.scratchcardlayout.ui.ScratchCardLayout
import dev.skymansandy.scratchcardlayout.util.ScratchCardUtils

class EventFragment : Fragment(), ScratchListener {

    lateinit var binding: FragmentEventBinding
    private val viewModel by viewModels<EventViewModel> { getVmFactory() }
    private lateinit var scratchCardView: View
    private var isWinPrize: Boolean = false
    private lateinit var prize: Prize
    lateinit var eventInfo: EventInfo

    lateinit var scratchCardDialog: AlertDialog
    private var cancelCardDialog: AlertDialog? = null
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
            if (null != eventInfo.event) {
                if (eventInfo.event!!.totalPrizeLeft == 0) {
                    setTextToToast(eventInfo.message ?: getString(R.string.no_card_left))
                } else {
                    viewModel.getPrizeByEventId()
                }
            } else {
                setTextToToast(getString(R.string.error_msg_contact_it))
            }
        }

        viewModel.eventInfo.observe(viewLifecycleOwner, Observer {
            it?.let {
                eventInfo = it
            }
        })

        viewModel.prize.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.totalPrizeLeft == 0) {
                    setTextToToast(it.message ?: getString(R.string.no_card_left))
                } else {
                    prize = it
                    getScratchCardDialog(it)
                    viewModel.getPrizeDone()
                }
            }
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                when (it) {
                    LoadApiStatus.LOADING -> showDialog(loadingDialog)
                    LoadApiStatus.DONE, LoadApiStatus.ERROR -> dismissDialog(loadingDialog)
                }
            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Logger.d("onResume getEventInfo")
    }

    @SuppressLint("InflateParams")
    private fun getScratchCardDialog(prize: Prize) {

        val builder = AlertDialog.Builder(context)
        scratchCardView = LayoutInflater.from(requireContext()).inflate(
            R.layout.component_scratch_card,
            null
        )

        val scratchCard = scratchCardView.findViewById<ScratchCardLayout>(R.id.scratchCard)
        scratchCard.setScratchListener(this)
        scratchCard.apply {
            scratchCard.setRevealFullAtPercent(90)
            scratchCard.setScratchWidthDip(ScratchCardUtils.dipToPx(requireContext(), 20F))
        }

        builder.setView(scratchCardView)
        scratchCardDialog = builder.create()

        scratchCardDialog.apply {

            setOnDismissListener {
                viewModel.getEventInfo()
            }

            setOnKeyListener { _, keyCode, _ ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    setupCancelDialog()
                    cancelCardDialog?.show()
                }

                keyCode == KeyEvent.KEYCODE_BACK
            }

            show()
        }

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

    override fun onScratchProgress(
        scratchCardLayout: ScratchCardLayout,
        atLeastScratchedPercent: Int
    ) {
        Logger.d("Progress = $atLeastScratchedPercent")
    }

    override fun onScratchComplete() {
        Logger.d("Scratch ended")
        scratchCardView.findViewById<TextView>(R.id.text_scratch_result_title).apply {
            show()
            text = when (isWinPrize) {
                true -> context.getString(R.string.win_prize)
                else -> context.getString(R.string.lose_prize)
            }
        }
        scratchCardView.findViewById<TextView>(R.id.text_scratch_result).apply {
            show()
            text = when (isWinPrize) {
                true -> prize.prizeCouponTitle
                else -> context.getString(R.string.lose_prize_note)
            }
        }
    }

    private fun setupCancelDialog(): AlertDialog {
        if (cancelCardDialog == null) {
            val builder = AlertDialog.Builder(context)
            builder.apply {
                setTitle(getString(R.string.sure_to_leave))
                setPositiveButton(getString(R.string.confirm)) { _: DialogInterface, _: Int ->
                    scratchCardDialog.dismiss()
                }
                setNegativeButton(getString(R.string.cancel)) { _: DialogInterface, _: Int ->
                    cancelCardDialog!!.dismiss()
                }
            }
            cancelCardDialog = builder.create()
        }
        return cancelCardDialog!!
    }
}