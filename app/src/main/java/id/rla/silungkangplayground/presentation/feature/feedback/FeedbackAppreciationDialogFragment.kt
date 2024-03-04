package id.rla.silungkangplayground.presentation.feature.feedback

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.databinding.FragmentFeedbackAppreciationDialogBinding
import id.rla.silungkangplayground.databinding.FragmentFeedbackDialogBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment

class FeedbackAppreciationDialogFragment : RoundedDialogFragment<FragmentFeedbackAppreciationDialogBinding>() {


    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedbackAppreciationDialogBinding {
        return FragmentFeedbackAppreciationDialogBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            acbOk.setOnClickListener {
                dialog?.dismiss()
            }
        }
    }
}