package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.util.FragmentActivityCallback
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentMemberHistoryBinding
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.MemberHistoryAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel.MemberHistoryViewModel
import id.rla.silungkangplayground.presentation.util.obtainViewModel
import id.rla.silungkangplayground.presentation.util.showToast
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class MemberHistoryFragment : BindingFragment<FragmentMemberHistoryBinding>(){

    private lateinit var viewModel:MemberHistoryViewModel
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
        viewModel = obtainViewModel(requireActivity().applicationContext)
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
            viewLifecycleOwner.lifecycleScope.launch {
                viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                    viewModel.uiState.collectLatest { uiState ->

                        if (rvVoucherHistory.adapter == null) rvVoucherHistory.adapter = adapter

                        adapter.submitList(uiState.listMemberHistory)

                        actvEmptyInfo.isVisible = uiState.listMemberHistory.isEmpty()

                        progressBar.isVisible = uiState.isLoading

                        uiState.toastMessage?.getContentIfNotHandled()?.let {
                            requireActivity().showToast(it)
                        }
                    }
                }
            }

        }

    }
}