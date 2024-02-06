package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentVoucherBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherItemDecoration
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.feedback.FeedbackDialogFragment
import id.rla.silungkangplayground.presentation.util.obtainViewModel
import id.rla.silungkangplayground.presentation.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.random.Random


class VoucherFragment : BindingFragment<FragmentVoucherBinding>(),
    OnProcessingVoucherExchangeListener {

    private lateinit var viewModel: VoucherViewModel

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
        viewModel = obtainViewModel(requireActivity().applicationContext)
        initRvAdapter()
        initButtons()
        initObserver()


        Random.nextInt(1, 4).also {
            if (it % 2 == 0) showReviewDialogFragment()
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.uiState.collectLatest { uiState ->
                    uiState.apply {
                        binding?.apply {
                            memberVoucherInfo?.apply {
                                actvEmptyInfo.isVisible = listVoucher.isEmpty()

                                val adapter = ActiveVoucherAdapter(listVoucher)
                                rvActiveVoucher.adapter = adapter

                                actvActiveVoucherCount.text = activeVoucherCount.toString()
                                actvPointCount.text = point


                            }

                            progressBar.isVisible = isLoading

                            toastMessage?.getContentIfNotHandled()?.let {
                                requireActivity().showToast(it)
                            }
                        }
                    }
                }
            }
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

            actvHistory.setOnClickListener {
                view?.findNavController()?.navigate(
                    R.id.action_menu_voucher_to_voucherHistoryFragment
                )
            }

        }
    }

    private fun showVoucherOptionDialog() {
        val voucherOptionDialogFragment = VoucherOptionDialogFragment()
        voucherOptionDialogFragment.show(childFragmentManager, null)
    }

    private fun initRvAdapter() {
        binding?.apply {
            viewModel.fetchDataPeriodically(viewLifecycleOwner)
            /*val adapter = ActiveVoucherAdapter(Dummy.getVouchers())
            rvActiveVoucher.adapter = adapter*/
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