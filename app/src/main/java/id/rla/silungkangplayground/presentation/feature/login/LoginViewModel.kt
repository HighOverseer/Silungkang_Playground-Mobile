package id.rla.silungkangplayground.presentation.feature.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.usecase.LoginUseCase
import id.rla.silungkangplayground.presentation.util.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUIState())
    val uiState: StateFlow<LoginUIState> = _uiState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun login(phoneNumber: String, password: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(isLoading = true)
            }

            val resource = loginUseCase(
                phoneNumber, password
            )
            when (resource) {
                is Resource.Success -> {
                    _uiEvent.send(UIEvent.ToastMessageEvent(resource.data))
                    _uiEvent.send(UIEvent.OnUserAuthenticatedEvent)
                }

                is Resource.Failure -> {
                    _uiEvent.send(UIEvent.ToastMessageEvent(resource.message))
                }

                is Resource.Error -> {
                    _uiEvent.send(
                        UIEvent.ToastMessageEvent(
                            DynamicString(
                                resource.e.message.toString()
                            )
                        )
                    )
                }

            }
        }.invokeOnCompletion{
            _uiState.update {
                it.copy(isLoading = false)
            }
        }
    }

}
