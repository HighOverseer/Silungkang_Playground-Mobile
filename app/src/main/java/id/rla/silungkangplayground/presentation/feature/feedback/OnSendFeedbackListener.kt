package id.rla.silungkangplayground.presentation.feature.feedback

interface OnSendFeedbackListener {
    fun sendFeedback(rating:Int = 3, content:String ="", isSubmitted:Boolean)
}