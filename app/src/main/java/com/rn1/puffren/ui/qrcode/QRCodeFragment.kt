package com.rn1.puffren.ui.qrcode

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.rn1.puffren.data.BuyDetail
import com.rn1.puffren.databinding.FragmentQRCodeBinding
import com.rn1.puffren.ext.getVmFactory
import com.rn1.puffren.ext.hideKeyboard
import com.rn1.puffren.util.ENCODE_UTF_8
import com.squareup.moshi.Moshi

class QRCodeFragment : Fragment() {

    lateinit var binding: FragmentQRCodeBinding
    private val moshi by lazy { Moshi.Builder().build() }
    private val jsonAdapter = moshi.adapter(BuyDetail::class.java)

    val viewModel by viewModels<QRCodeViewModel> {
        getVmFactory(
            QRCodeFragmentArgs.fromBundle(
                requireArguments()
            ).user
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentQRCodeBinding.inflate(inflater, container, false)

        val input = binding.editEnterCheckoutAmount

        binding.buttonCreateCode.setOnClickListener {

            if (input.text.toString().trim().isEmpty()) {
                return@setOnClickListener
            }

            try {

                //TODO TEST DATA
                val buyDetail = BuyDetail(
                    "nihou",
                    "折20",
                    200,
                    20,
                    100,
                    input.text.toString().trim().toInt()
                )

                val jsonString = jsonAdapter.toJson(buyDetail)

                //Initialize bitmap
                val bitmap = create2DCode(jsonString)
                binding.imageQrcodeOutput.setImageBitmap(bitmap)
                hideKeyboard()

            } catch (e: WriterException) {
                e.printStackTrace()
            }
        }

        return binding.root
    }


    private fun create2DCode(str: String): Bitmap? {
        // create two-way array, setup size before create bitmap
        val hints = mutableMapOf<EncodeHintType, String>()
        hints[EncodeHintType.CHARACTER_SET] = ENCODE_UTF_8
        val matrix = MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 350, 350, hints)
        val width = matrix.width
        val height = matrix.height

        val pixels = IntArray(width * height)
        for (y in 0 until height) {
            for (x in 0 until width) {
                if (matrix[x, y]) {
                    pixels[y * width + x] = -0x1000000
                }
            }
        }
        val bitmap = Bitmap.createBitmap(
            width, height,
            Bitmap.Config.ARGB_8888
        )
        // create bitmap by pixels IntArray
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height)
        return bitmap
    }
}