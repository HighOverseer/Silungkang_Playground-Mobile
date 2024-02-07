package id.rla.silungkangplayground.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.SingleEvent
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
):ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading:LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<SingleEvent<StringRes>>()
    val toastMessage:LiveData<SingleEvent<StringRes>> = _toastMessage

    private val _userAuthenticatedEvent = MutableLiveData<SingleEvent<Unit>>()
    val userAuthenticatedEvent:LiveData<SingleEvent<Unit>> = _userAuthenticatedEvent


    fun login(memberId:String,password:String){
        _isLoading.value = true
        viewModelScope.launch {
            val resource = loginUseCase(
                memberId, password
            )
            when(resource){
                is Resource.Success -> {
                    _toastMessage.value = SingleEvent(resource.data)
                    _userAuthenticatedEvent.value = SingleEvent(Unit)
                }
                is Resource.Failure -> {
                    _toastMessage.value = SingleEvent(resource.message)
                }
                is Resource.Error -> {
                    _toastMessage.value = SingleEvent(
                        DynamicString(
                            resource.e.message.toString()
                        )
                    )
                }

            }
            _isLoading.value = false

        }
    }

}
