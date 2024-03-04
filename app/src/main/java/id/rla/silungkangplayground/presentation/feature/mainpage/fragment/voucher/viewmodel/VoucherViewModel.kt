package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.usecase.ExchangePointUseCase
import id.rla.silungkangplayground.domain.usecase.GetDetailMemberVoucherUseCase
import id.rla.silungkangplayground.domain.usecase.GetOfferedVouchersUseCase
import id.rla.silungkangplayground.domain.usecase.SendFeedbackUseCase
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate.VoucherUiState
import id.rla.silungkangplayground.presentation.util.UIEvent
import id.rla.silungkangplayground.presentation.util.doSomethingOnlyLifeCycleStarted
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VoucherViewModel @Inject constructor(
    private val getDetailMemberVoucherUseCase: GetDetailMemberVoucherUseCase,
    private val getOfferedVouchersUseCase: GetOfferedVouchersUseCase,
    private val exchangePointUseCase: ExchangePointUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VoucherUiState(isLoadingDetailUser = true))
    val uiState: StateFlow<VoucherUiState> = _uiState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private suspend fun getMemberVoucherInfo() {
        _uiState.update {
            it.copy(isLoadingDetailUser = true)
        }
        when (val resource = getDetailMemberVoucherUseCase()) {
            is Resource.Success -> _uiState.update {
                it.copy(
                    memberVoucherInfo = resource.data,
                    isLoadingDetailUser = false
                )
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        isLoadingDetailUser = false
                    )
                }
                _uiEvent.send(UIEvent.ToastMessageEvent(resource.message))
            }

            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoadingDetailUser = false
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

    private suspend fun getOfferedVouchers(){
        _uiState.update {
            it.copy(isLoadingOfferedVouchers = true)
        }
        when (val resource = getOfferedVouchersUseCase()) {
            is Resource.Success -> _uiState.update {
                it.copy(
                    isLoadingOfferedVouchers = false,
                    offeredVouchers = resource.data
                )
            }

            is Resource.Failure -> {
                _uiState.update {
                    it.copy(
                        isLoadingOfferedVouchers = false
                    )
                }
                _uiEvent.send(UIEvent.ToastMessageEvent(resource.message))
            }

            is Resource.Error -> {
                _uiState.update {
                    it.copy(
                        isLoadingOfferedVouchers = false
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

    fun updateDetailMemberPeriodically(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.doSomethingOnlyLifeCycleStarted {
            var isFirstTime = true

            while (true){
                if (isFirstTime) {
                    isFirstTime = false
                    delay(0)
                }else delay(DELAY_REFRESH_DETAIL_MEMBER_DATA)
                getMemberVoucherInfo()
            }
        }.invokeOnCompletion {cause ->
            if (cause != null) _uiState.update {
                it.copy(
                    isLoadingDetailUser = false
                )
            }
        }
    }

    fun updateOfferedVouchersPeriodically(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.doSomethingOnlyLifeCycleStarted {
            var isFirstTime = true

            while (true){
                if (isFirstTime) {
                    isFirstTime = false
                    delay(0)
                }else delay(DELAY_REFRESH_OFFERED_VOUCHERS_DATA)

                getOfferedVouchers()
            }
        }.invokeOnCompletion {cause ->
            if (cause != null) _uiState.update {
                it.copy(
                    isLoadingOfferedVouchers = false
                )
            }
        }
    }

    fun exchangePoint(voucherTypeId:Int){
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoadingDetailUser = true)
            }
            when(val res = exchangePointUseCase(voucherTypeId)){
                is Resource.Success -> {
                    res.message?.let {
                        _uiEvent.send(
                            UIEvent.ToastMessageEvent(it)
                        )
                    }
                    _uiState.update {
                        it.copy(
                            isLoadingDetailUser = false,
                            memberVoucherInfo = res.data
                        )
                    }
                    _uiEvent.send(UIEvent.OnExchangePointSuccess)
                }
                is Resource.Failure -> {
                    _uiState.update {
                        it.copy(
                            isLoadingDetailUser = false
                        )
                    }
                    _uiEvent.send(UIEvent.ToastMessageEvent(res.message))
                    _uiEvent.send(UIEvent.OnExchangePointFailed)
                }

                is Resource.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoadingDetailUser = false
                        )
                    }
                    _uiEvent.send(
                        UIEvent.ToastMessageEvent(
                            DynamicString(
                                res.e.toString()
                            )
                        )
                    )
                }
            }
        }
    }

    companion object{
        private const val DELAY_REFRESH_DETAIL_MEMBER_DATA = 30_000L //30s
        private const val DELAY_REFRESH_OFFERED_VOUCHERS_DATA = 60_000L
    }
}