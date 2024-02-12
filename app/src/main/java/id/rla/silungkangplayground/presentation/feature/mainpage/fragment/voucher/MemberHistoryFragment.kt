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
import id.rla.silungkangplayground.databinding.FragmentMemberHistoryBinding
import id.rla.silungkangplayground.domain.common.Event
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.MemberHistoryAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel.MemberHistoryViewModel
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.showToast


@AndroidEntryPoint
class MemberHistoryFragment : BindingFragment<FragmentMemberHistoryBinding>(){

    private val viewModel:MemberHistoryViewModel by viewModels()
    private val adapter = MemberHistoryAdapter()

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMemberHistoryBinding {
        return FragmentMemberHistoryBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initNavigation()
        initObserver()
        viewModel.fetchDataPeriodically(viewLifecycleOwner)
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
                if (rvVoucherHistory.adapter == null) rvVoucherHistory.adapter = adapter

                adapter.submitList(uiState.listMemberHistory)

                actvEmptyInfo.isVisible = uiState.listMemberHistory.isEmpty()

                progressBar.isVisible = uiState.isLoading

            }

            viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){ uiEvent ->
                if (uiEvent is UIEvent.ToastMessageEvent) requireActivity().showToast(uiEvent.message)
            }
        }

    }
}