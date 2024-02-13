package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.card_member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.databinding.FragmentCardMemberBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.CardMemberAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.collectChannelFlowOnLifecycleStarted
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import id.rla.silungkangplayground.presentation.util.showToast
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CardMemberFragment : BindingFragment<FragmentCardMemberBinding>() {

    private val viewModel:CardMemberViewModel by viewModels()
    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCardMemberBinding {
        return FragmentCardMemberBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initObserver()
    }

    private fun initObserver(){
        binding?.apply {
            viewLifecycleOwner.collectLatestOnLifeCycleStarted(viewModel.uiState){
                rvCardMember.adapter = CardMemberAdapter(it.listCardMember)

                actvEmptyInfo.isVisible = it.listCardMember.isEmpty()

                progressBar.isVisible = it.isLoading

            }
            viewLifecycleOwner.collectChannelFlowOnLifecycleStarted(viewModel.uiEvent){
                if (it is UIEvent.ToastMessageEvent) requireActivity().showToast(it.message)
            }
        }

    }

    private fun initAdapter(){
        binding?.apply {
            rvCardMember.addItemDecoration(GenericItemDecoration(resources.displayMetrics, paddingBottom = 10))
            viewModel.fetchDataPeriodically(viewLifecycleOwner)
        }

    }

}