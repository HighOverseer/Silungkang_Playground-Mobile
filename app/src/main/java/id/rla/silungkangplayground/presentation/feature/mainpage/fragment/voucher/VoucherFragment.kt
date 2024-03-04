package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentVoucherBinding
import id.rla.silungkangplayground.domain.usecase.SendFeedbackUseCase
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.ActiveVoucherItemDecoration
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.feedback.FeedbackDialogFragment
import id.rla.silungkangplayground.presentation.feature.feedback.OnSendFeedbackListener
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.OfferedVouchersAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.OnProcessingVoucherExchangeListener
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel.VoucherViewModel
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.showToast
import kotlin.random.Random


@AndroidEntryPoint
class VoucherFragment : BindingFragment<FragmentVoucherBinding>(),
    OnProcessingVoucherExchangeListener{

    private val viewModel: VoucherViewModel by viewModels()

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
        initObserver()

    }

    private fun initObserver() {
        viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.uiState){ uiState ->
            uiState.apply {
                binding?.apply {
                    memberVoucherInfo?.apply {
                        actvEmptyInfo.isVisible = listVoucher.isEmpty()

                        rvActiveVoucher.swapAdapter(
                            ActiveVoucherAdapter(listVoucher),
                            false
                        )

                        actvActiveVoucherCount.text = activeVoucherCount.toString()
                        actvPointCount.text = point
                    }

                    progressBar.isVisible = isLoadingDetailUser || isLoadingOfferedVouchers

                    val offeredVouchers = if (isLoadingOfferedVouchers) emptyList() else uiState.offeredVouchers
                    rvVoucherExchangeOption.adapter =  OfferedVouchersAdapter(
                        offeredVouchers,
                        onItemClicked = { showExchangePointConfirmationDialog(it.typeId) }
                    )
                    /*rvVoucherExchangeOption.swapAdapter(
                        OfferedVouchersAdapter(offeredVouchers, onItemClicked = { showExchangePointConfirmationDialog(it.typeId) }),
                        true,
                    )*/
                }
            }
        }

        viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){ uiEvent ->
            when(uiEvent) {
                is UIEvent.ToastMessageEvent -> requireActivity().showToast(uiEvent.message)
                is UIEvent.OnExchangePointSuccess -> showExchangeSuccessDialog()
                is UIEvent.OnExchangePointFailed -> showExchangeFailedDialog()

                else -> Unit
            }
        }
    }




    private fun initButtons() {
        binding?.apply {
            actvHistory.setOnClickListener {
                view?.findNavController()?.navigate(
                    R.id.action_menu_voucher_to_memberHistoryFragment
                )
            }

        }
    }

    private fun showExchangePointConfirmationDialog(voucherTypeId:Int) {
        val exchangePointConfirmationDialogFragment = ExchangePointConfirmationDialogFragment()
        exchangePointConfirmationDialogFragment.setVoucherTypeId(voucherTypeId)
        exchangePointConfirmationDialogFragment.show(childFragmentManager, null)
    }

    private fun initRvAdapter() {
        binding?.apply {
            rvVoucherExchangeOption.layoutManager = LinearLayoutManager(
                requireActivity(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            rvVoucherExchangeOption.addItemDecoration(
                GenericItemDecoration(
                    resources.displayMetrics,
                    paddingEnd = 8
                )
            )
            rvActiveVoucher.addItemDecoration(
                ActiveVoucherItemDecoration(
                    RV_VOUCHER_SEPARATOR_MARGIN
                        .toDp()
                        .toInt()
                )
            )
            viewModel.updateDetailMemberPeriodically(viewLifecycleOwner)
            viewModel.updateOfferedVouchersPeriodically(viewLifecycleOwner)
        }
    }

    override fun onProcessExchange(voucherTypeId: Int) {
        viewModel.exchangePoint(voucherTypeId)
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