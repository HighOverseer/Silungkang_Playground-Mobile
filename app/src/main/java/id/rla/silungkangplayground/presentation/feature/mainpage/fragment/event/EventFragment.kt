package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.rla.silungkangplayground.databinding.FragmentEventBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.EventAdapter
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration


class EventFragment : BindingFragment<FragmentEventBinding>() {

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
    }

    private fun initAdapter() {
        binding?.apply {
            rvEvent.adapter = EventAdapter().also { it.submitList(Dummy.getListEvent().toMutableList()) }
            rvEvent.addItemDecoration(GenericItemDecoration(resources.displayMetrics, paddingBottom = 10))
            rvEvent.layoutManager = LinearLayoutManager(requireActivity())
        }
    }
}