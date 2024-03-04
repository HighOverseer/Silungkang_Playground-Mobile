package id.rla.silungkangplayground.presentation.feature.feedback

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import id.rla.silungkangplayground.databinding.FragmentFeedbackDialogBinding
import id.rla.silungkangplayground.presentation.customview.RoundedDialogFragment
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.VoucherFragment

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
                sendFeedback(true)
                dialog?.dismiss()
            }
            setStarsListener()
        }

    }


    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        sendFeedback(false)
    }
    private fun sendFeedback(isSubmitted:Boolean) {
        binding?.apply {

            val activity = requireActivity()
            if (activity is OnSendFeedbackListener){
                if (!isSubmitted){
                    activity.sendFeedback(isSubmitted = false)
                    return
                }

                val rating = getRating()
                val content = acetReview.text.toString().trim()
                activity.sendFeedback(rating, content, true)
            }
        }
    }

    private fun getRating():Int{
        var rating = 0
        acivStars?.forEach {
            if (it.isActivated){
                rating++
            }
        }
        return rating
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