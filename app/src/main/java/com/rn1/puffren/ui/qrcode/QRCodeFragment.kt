package com.rn1.puffren.ui.qrcode

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.journeyapps.barcodescanner.BarcodeEncoder
import com.rn1.puffren.R
import com.rn1.puffren.databinding.FragmentQRCodeBinding
import com.rn1.puffren.util.Util.hideKeyBoard


class QRCodeFragment : Fragment() {

    lateinit var binding: FragmentQRCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQRCodeBinding.inflate(inflater, container, false)

        val input = binding.editEnterCheckoutAmount

        binding.buttonCreateCode.setOnClickListener {
            val writer = MultiFormatWriter()

            try {
                //Initialize bit matrix
                val bitMatrix = writer.encode(input.text.toString().trim(), BarcodeFormat.QR_CODE, 350, 350)
                //Initialize barcode encoder
                val encoder = BarcodeEncoder()
                //Initialize bitmap
                val bitmap = encoder.createBitmap(bitMatrix)
                binding.imageQrcodeOutput.setImageBitmap(bitmap)

                hideKeyBoard(input)
            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }


        return binding.root
    }
}