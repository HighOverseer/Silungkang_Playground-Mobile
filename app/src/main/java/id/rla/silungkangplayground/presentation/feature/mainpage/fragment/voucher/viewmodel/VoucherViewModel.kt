package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.viewmodel

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.SingleEvent
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.presentation.feature.mainpage.fragment.voucher.uistate.VoucherUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VoucherViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _uiState = MutableStateFlow(VoucherUiState(isLoading = true))
    val uiState: StateFlow<VoucherUiState> = _uiState
    private suspend fun getMemberVoucherInfo() {
        _uiState.value = _uiState.value.copy(isLoading = true)
        _uiState.value = when (val resource = repository.getDetailMemberVoucher()) {
            is Resource.Success -> _uiState.value.copy(
                memberVoucherInfo = resource.data,
                isLoading = false
            )

            is Resource.Failure -> _uiState.value.copy(
                toastMessage = SingleEvent(resource.message),
                isLoading = false
            )

            is Resource.Error -> _uiState.value.copy(
                toastMessage = SingleEvent(
                    DynamicString(
                        resource.e.toString()
                    )
                ), isLoading = false
            )
        }
    }

    fun fetchDataPeriodically(lifecycleOwner: LifecycleOwner){
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED){
                var isFirstTime = true

                while (true){
                    if (isFirstTime) {
                        isFirstTime = false
                        delay(0)
                    }else delay(DELAY_REFRESH_DATA)

                    getMemberVoucherInfo()
                }

            }
        }

    }

    companion object{
        private const val DELAY_REFRESH_DATA =60_000L //30s
    }
}