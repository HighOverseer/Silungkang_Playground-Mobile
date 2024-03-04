package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.FragmentActivityCallback
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentVoucherHistoryBinding
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.VoucherHistoryAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel.MemberHistoryViewModel
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.showToast


@AndroidEntryPoint
class VoucherHistoryFragment : BindingFragment<FragmentVoucherHistoryBinding>(){

    private val viewModel:MemberHistoryViewModel by viewModels()

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVoucherHistoryBinding {
        return FragmentVoucherHistoryBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
        viewModel.fetchDataPeriodically(viewLifecycleOwner)
        initObserver()
    }

    private fun initNavigation() {
        val parentActivity = requireActivity()
        if (parentActivity is FragmentActivityCallback){
            parentActivity.keepBottomNavSelected(R.id.menu_voucher)
        }
    }

    private fun initObserver(){
        binding?.apply {

            viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.uiState){ uiState ->

                rvVoucherHistory.swapAdapter(
                    VoucherHistoryAdapter(uiState.listMemberHistory),
                    false
                )
                actvEmptyInfo.isVisible = uiState.listMemberHistory.isEmpty() && !uiState.isLoading
                progressBar.isVisible = uiState.isLoading
            }


            viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){ uiEvent ->
                if (uiEvent is UIEvent.ToastMessageEvent) requireActivity().showToast(uiEvent.message)
            }
        }

    }
}