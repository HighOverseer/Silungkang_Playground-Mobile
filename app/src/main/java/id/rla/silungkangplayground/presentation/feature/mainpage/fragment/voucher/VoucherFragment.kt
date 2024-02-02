package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import id.rla.silungkangplayground.databinding.FragmentVoucherBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherItemDecoration
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.feedback.FeedbackDialogFragment
import kotlin.random.Random


class VoucherFragment : BindingFragment<FragmentVoucherBinding>(),
    OnProcessingVoucherExchangeListener {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVoucherBinding {
        return FragmentVoucherBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRvAdapter()
        initButtons()

        Random.nextInt(1, 4).also {
            if (it % 2 == 0) showReviewDialogFragment()
        }
    }

    private fun showReviewDialogFragment(){
        val fragment = FeedbackDialogFragment()
        fragment.show(childFragmentManager, null)
    }

    private fun initButtons() {
        binding?.apply {
            val exchangePointButtonListener = View.OnClickListener {
                showVoucherOptionDialog()
            }
            acbExchangeButton1.setOnClickListener(exchangePointButtonListener)
            acbExchangeButton2.setOnClickListener(exchangePointButtonListener)
        }
    }

    private fun showVoucherOptionDialog() {
        val voucherOptionDialogFragment = VoucherOptionDialogFragment()
        voucherOptionDialogFragment.show(childFragmentManager, null)
    }

    private fun initRvAdapter() {
        binding?.apply {
            val adapter = ActiveVoucherAdapter(Dummy.getVouchers())
            rvActiveVoucher.adapter = adapter
            rvActiveVoucher.addItemDecoration(
                ActiveVoucherItemDecoration(
                    RV_VOUCHER_SEPARATOR_MARGIN
                        .toDp()
                        .toInt()
                )
            )
        }
    }

    override fun onProcess() {
        binding?.apply {
            progressBar.isVisible = true
            Random.nextInt(1,3).also {
                print(it)
                if (it % 2 == 1){
                    showExchangeSuccessDialog()
                }else showExchangeFailedDialog()
            }

        }
    }


    private fun showExchangeSuccessDialog() {
        binding?.progressBar?.isVisible = false
        val fragment = ExchangeSuccessDialogFragment()
        fragment.show(childFragmentManager, null)
    }

    private fun showExchangeFailedDialog() {
        binding?.progressBar?.isVisible = false
        val fragment = ExchangeFailedDialogFragment()
        fragment.show(childFragmentManager, null)
    }


    private fun Float.toDp():Float{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, resources.displayMetrics)
    }

    companion object{
        private const val RV_VOUCHER_SEPARATOR_MARGIN = 11f
    }

}