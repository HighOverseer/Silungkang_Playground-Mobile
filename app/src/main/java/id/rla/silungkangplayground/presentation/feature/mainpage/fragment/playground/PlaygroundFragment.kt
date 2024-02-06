package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.playground

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.rla.silungkangplayground.databinding.FragmentPlaygroundBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.domain.model.OperationalHours
import id.rla.silungkangplayground.domain.model.PlaygroundModel
import id.rla.silungkangplayground.domain.model.TicketInfo
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.GenericItemDecoration
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.PlaygroundAdapter

class PlaygroundFragment : BindingFragment<FragmentPlaygroundBinding>() {

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentPlaygroundBinding {
        return FragmentPlaygroundBinding.inflate(
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
        val adapter = PlaygroundAdapter(getItems())
        binding?.apply {
            rvPlayground.adapter = adapter
            rvPlayground.addItemDecoration(GenericItemDecoration(resources.displayMetrics, paddingBottom = 10))
        }
    }

    private fun getItems():List<PlaygroundModel>{
        return Dummy.getPlaygroundModel()
    }
}