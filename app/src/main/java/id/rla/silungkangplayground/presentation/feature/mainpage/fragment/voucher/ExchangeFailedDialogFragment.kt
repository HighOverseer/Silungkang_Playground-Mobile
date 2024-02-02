package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rla.silungkangplayground.databinding.FragmentExchangeFailedDialogBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment

class ExchangeFailedDialogFragment : RoundedDialogFragment<FragmentExchangeFailedDialogBinding>() {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentExchangeFailedDialogBinding {
        return FragmentExchangeFailedDialogBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            acbOk.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }

}