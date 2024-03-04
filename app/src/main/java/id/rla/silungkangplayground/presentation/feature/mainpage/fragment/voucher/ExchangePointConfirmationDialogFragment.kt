package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rla.silungkangplayground.databinding.FragmentPointExchangeConfirmationDialogBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.OnProcessingVoucherExchangeListener

class ExchangePointConfirmationDialogFragment : RoundedDialogFragment<FragmentPointExchangeConfirmationDialogBinding>() {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPointExchangeConfirmationDialogBinding {
       return FragmentPointExchangeConfirmationDialogBinding.inflate(layoutInflater, container, false)
    }

    private var voucherTypeId:Int?=null

    fun setVoucherTypeId(voucherTypeId:Int){
        this.voucherTypeId = voucherTypeId
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
    }


    private fun initButtons() {
        binding?.apply {
            acbYes.setOnClickListener{
                voucherTypeId?.let { id ->
                    val parent = parentFragment
                    if (parent is OnProcessingVoucherExchangeListener){
                        dialog?.dismiss()
                        parent.onProcessExchange(id)
                    }
                }
            }
            acbCancel.setOnClickListener{
                dialog?.dismiss()
            }
        }
    }

}