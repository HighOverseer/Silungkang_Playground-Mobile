package id.rla.silungkangplayground.presentation.util

import id.rla.silungkangplayground.domain.common.Event
import id.rla.silungkangplayground.domain.common.StringRes

sealed class UIEvent: Event(){
    data object OnUserAuthenticatedEvent:UIEvent()
    data class ToastMessageEvent(val message: StringRes): UIEvent()
}