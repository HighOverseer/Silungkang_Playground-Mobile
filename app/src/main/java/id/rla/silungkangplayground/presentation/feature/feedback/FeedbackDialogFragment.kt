package id.rla.silungkangplayground.presentation.feature.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import id.rla.silungkangplayground.databinding.FragmentFeedbackDialogBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment

class FeedbackDialogFragment : RoundedDialogFragment<FragmentFeedbackDialogBinding>() {

    private var acivStars:List<AppCompatImageView>? = emptyList()

    override fun onCreateBinding(
        layoutInflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFeedbackDialogBinding {
        return FragmentFeedbackDialogBinding.inflate(
            layoutInflater,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            acbSubmit.setOnClickListener {
                dialog?.dismiss()
            }
            setStarsListener()

        }

    }

    private fun setStarsListener() {

        acivStars = binding?.run {
            listOf(
                acivStar1,
                acivStar2,
                acivStar3,
                acivStar4,
                acivStar5
            )
        }

        acivStars?.also {
            (it.indices).forEach { index ->
                it[index].setOnClickListener {
                    rateStars(index)
                }
            }
        }
    }




    private fun rateStars(selectedStarIndex:Int){
        if (selectedStarIndex > 4 || selectedStarIndex < 0) return

        (0..selectedStarIndex).forEach { i ->
            acivStars?.get(i)?.also { it.isActivated = true }
        }

        val unfilledPosition = selectedStarIndex + 1
        if (unfilledPosition > 4) return

        (unfilledPosition..4).forEach { i ->
            acivStars?.get(i)?.also { it.isActivated = false }
        }

    }
}