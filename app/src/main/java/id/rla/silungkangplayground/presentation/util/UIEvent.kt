package id.rla.silungkangplayground.presentation.util

import id.rla.silungkangplayground.domain.common.Event
import id.rla.silungkangplayground.domain.common.StringRes

sealed class UIEvent: Event(){
    data object OnUserAuthenticatedEvent:UIEvent()
    data class ToastMessageEvent(val message: StringRes): UIEvent()
    data object OnUserRequiredToLoginEvent:UIEvent()
    data object OnUserSuccessfullyLogout:UIEvent()
    data object OnUserSuccessfullyChangeAccount:UIEvent()
    data object OnExchangePointSuccess:UIEvent()
    data object OnExchangePointFailed:UIEvent()
    data object UserFeedbackEvent:UIEvent()
    data object OnUserFeedbackSentEvent:UIEvent()
}