package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.card_member

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.usecase.GetCardMemberUseCase
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel.VoucherViewModel
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.doSomethingOnlyLifeCycleStarted
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardMemberViewModel @Inject constructor(
    private val getCardMemberUseCase: GetCardMemberUseCase
):ViewModel() {

    private val _uiState = MutableStateFlow(CardMemberUiState())
    val uiState:StateFlow<CardMemberUiState> = _uiState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()


    private suspend fun getCardMember() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        when (val resource = getCardMemberUseCase()) {
            is Resource.Success -> _uiState.update {
                it.copy(
                    listCardMember = resource.data,
                    isLoading = false
                )
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _uiEvent.send(UIEvent.ToastMessageEvent(resource.message))
            }

            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
                _uiEvent.send(
                    UIEvent.ToastMessageEvent(
                        DynamicString(
                            resource.e.toString()
                        )
                    )
                )
            }
        }
    }

    fun fetchDataPeriodically(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.doSomethingOnlyLifeCycleStarted {
            var isFirstTime = true

            while (true){
                if (isFirstTime) {
                    isFirstTime = false
                    delay(0)
                }else delay(DELAY_REFRESH_DATA)

                getCardMember()
            }
        }.invokeOnCompletion {cause ->
            if (cause != null) _uiState.update {
                it.copy(isLoading = false)
            }
        }

    }

    companion object{
        private const val DELAY_REFRESH_DATA = 60_000L
    }

}