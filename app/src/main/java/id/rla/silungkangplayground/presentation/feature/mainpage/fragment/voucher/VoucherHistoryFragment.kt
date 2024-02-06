package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.smartdrobi.aplikasipkm.ui.home.otherview.NonTopLevelFragmentCallback
import com.smartdrobi.aplikasipkm.ui.home.toplevelview.FragmentActivityCallback
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentVoucherHistoryBinding
import id.rla.silungkangplayground.domain.helper.Dummy
import id.rla.silungkangplayground.presentation.customview.BindingFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.adapter.VoucherHistoryAdapter


class VoucherHistoryFragment : BindingFragment<FragmentVoucherHistoryBinding>(){
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
        initAdapter()
    }

    private fun initNavigation() {
        val parentActivity = requireActivity()
        if (parentActivity is FragmentActivityCallback){
            parentActivity.keepBottomNavSelected(R.id.menu_voucher)
        }
    }

    private fun initAdapter(){
        binding?.apply {
            rvVoucherHistory.adapter = VoucherHistoryAdapter().also {
                it.submitList(Dummy.getVoucherHistoryItem())
            }

        }

    }
}