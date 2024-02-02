package id.rla.silungkangplayground.presentation.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rla.silungkangplayground.domain.core.DynamicString
import id.rla.silungkangplayground.domain.core.Resource
import id.rla.silungkangplayground.domain.core.SingleEvent
import id.rla.silungkangplayground.domain.core.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: Repository
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
            val resource = repository.login(
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
