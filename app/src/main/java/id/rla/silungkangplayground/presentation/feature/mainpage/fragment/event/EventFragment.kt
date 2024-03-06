package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.rla.silungkangplayground.databinding.FragmentEventBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.EventAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.EventPlaygroundPagingAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.LoadingStateAdapter
import id.rla.silungkangplayground.presentation.util.collectLatestOnLifeCycleStarted
import kotlinx.coroutines.flow.map


@AndroidEntryPoint
class EventFragment : BindingFragment<FragmentEventBinding>() {

    private val viewModel:EventViewModel by viewModels()
    private val adapter = EventPlaygroundPagingAdapter()

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentEventBinding {
        return FragmentEventBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()

        collectLatestOnLifeCycleStarted(viewModel.eventPlaygroundsPagings){
            adapter.submitData(lifecycle, it)
        }

    }

    private fun initAdapter() {
        binding?.apply {
            adapter.withLoadStateFooter(
                footer = LoadingStateAdapter{
                    adapter.retry()
                }
            )
            rvEvent.adapter = adapter
            rvEvent.addItemDecoration(GenericItemDecoration(resources.displayMetrics, paddingBottom = 10))
            rvEvent.layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}