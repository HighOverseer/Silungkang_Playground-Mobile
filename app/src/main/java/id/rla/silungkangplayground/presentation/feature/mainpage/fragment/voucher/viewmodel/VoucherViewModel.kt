package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate.VoucherUiState
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.doSomethingOnlyLifeCycleStarted
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(
    private val getDetailMemberVoucherUseCase: GetDetailMemberVoucherUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VoucherUiState(isLoading = true))
    val uiState: StateFlow<VoucherUiState> = _uiState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun getMemberVoucherInfo() {
        _uiState.update {
            it.copy(isLoading = true)
        }
        when (val resource = getDetailMemberVoucherUseCase()) {
            is Resource.Success -> _uiState.update {
                it.copy(
                    memberVoucherInfo = resource.data,
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

                getMemberVoucherInfo()
            }
        }.invokeOnCompletion {cause ->
            if (cause != null) _uiState.update {
                it.copy(isLoading = false)
            }
        }

    }

    companion object{
        private const val DELAY_REFRESH_DATA = 60_000L //30s
    }
}