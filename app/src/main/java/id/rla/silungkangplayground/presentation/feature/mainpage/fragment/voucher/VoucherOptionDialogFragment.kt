package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rla.silungkangplayground.databinding.FragmentVoucherOptionBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment

class VoucherOptionDialogFragment : RoundedDialogFragment<FragmentVoucherOptionBinding>() {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVoucherOptionBinding {
       return FragmentVoucherOptionBinding.inflate(layoutInflater, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }


    private fun initButtons() {
        binding?.apply {
            val clickListener = View.OnClickListener {
                val parent = parentFragment
                if (parent is OnProcessingVoucherExchangeListener){
                    dialog?.dismiss()
                    parent.onProcess()
                }
            }
            acbPlayground.setOnClickListener(clickListener)
            acbCafe.setOnClickListener(clickListener)
        }
    }

}