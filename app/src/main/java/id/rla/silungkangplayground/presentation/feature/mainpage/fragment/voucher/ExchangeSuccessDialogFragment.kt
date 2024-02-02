package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentExchangeSuccessDialogBinding
import id.rla.silungkangplayground.presentation.util.loadImage
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment

class ExchangeSuccessDialogFragment : RoundedDialogFragment<FragmentExchangeSuccessDialogBinding>() {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExchangeSuccessDialogBinding {
        return FragmentExchangeSuccessDialogBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            acivSuccessGif.loadImage(R.drawable.exchange_success_animation)
            acbOk.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }
}