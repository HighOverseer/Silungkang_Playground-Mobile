package id.rla.silungkangplayground.presentation.feature.mainpage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.usecase.ChangeCurrentMemberAccountUseCase
import id.rla.silungkangplayground.domain.usecase.CheckInMemberUseCase
import id.rla.silungkangplayground.domain.usecase.GetCurrentMemberIdUseCase
import id.rla.silungkangplayground.domain.usecase.LogoutUseCase
import id.rla.silungkangplayground.domain.usecase.SendFeedbackUseCase
import id.rla.silungkangplayground.presentation.util.UIEvent
import kotlinx.coroutines.CompletionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainPageViewModel @Inject constructor(
    private val checkInMemberUseCase: CheckInMemberUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val changeCurrentMemberAccountUseCase: ChangeCurrentMemberAccountUseCase,
    private val sendFeedbackUseCase: SendFeedbackUseCase,
    getCurrentMemberIdUseCase: GetCurrentMemberIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainPageUiState())
    val uiState: StateFlow<MainPageUiState> = _uiState

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var job: Job?= null

    private val completionHandler = object: CompletionHandler{
        override fun invoke(cause: Throwable?) {
            if (cause != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }
        }
    }
    private suspend fun checkInMember(newMemberId:String, oldMemberId:String?) {
        _uiState.update {
            it.copy(
                isLoading = true
            )
        }

        when (val resource = checkInMemberUseCase(newMemberId)) {
            is Resource.Success -> {
                val currentMemberName = resource
                    .data
                    .listMemberAccount
                    .first{ it.id == newMemberId }
                    .name

                _uiState.update { state ->
                    state.copy(
                        isLoading = false,
                        currentMemberAccountId = newMemberId,
                        listMemberAccount = resource.data.listMemberAccount
                    )
                }
                resource.message ?: return
                _uiEvent.send(
                    UIEvent.ToastMessageEvent(StaticString(R.string.anda_sekarang_login_sebagai, currentMemberName))
                )
                if (!oldMemberId.isNullOrEmpty() && oldMemberId != newMemberId){
                    _uiEvent.send(
                        UIEvent.OnUserSuccessfullyChangeAccount
                    )
                }
                if (resource.data.sentFeedbackEvent){
                    _uiEvent.send(
                        UIEvent.UserFeedbackEvent
                    )
                }
                _uiState.update { it.copy(isLoading = false) }
                return
            }

            is Resource.Failure -> {
                _uiEvent.send(
                    UIEvent.ToastMessageEvent(
                        resource.message
                    )
                )

                val listMember = resource.data?.listMemberAccount ?: emptyList()
                if (listMember.any { it.id == oldMemberId }) {
                    _uiState.update { state ->
                        state.copy(
                            isLoading = false,
                            currentMemberAccountId = oldMemberId,
                            listMemberAccount = listMember
                        )
                    }
                    return
                }
                _uiEvent.send(
                    UIEvent.OnUserRequiredToLoginEvent
                )
                _uiState.update { it.copy(isLoading = false) }

            }

            is Resource.Error -> {
                UIEvent.ToastMessageEvent(
                    DynamicString(
                        resource.e.toString()
                    )
                )


                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }


    fun logout() {
        viewModelScope.launch {
            job?.cancel()

            logoutUseCase().apply {
                when (this) {
                    is Resource.Success -> {
                        message?.let {
                            _uiEvent.send(
                                UIEvent.ToastMessageEvent(it)
                            )
                        }
                        _uiEvent.send(
                            UIEvent.OnUserSuccessfullyLogout
                        )
                    }

                    else -> Unit
                }
            }
        }
    }

    fun changeCurrentMemberAccount(memberId: String) {
        viewModelScope.launch {
            changeCurrentMemberAccountUseCase(memberId)
        }

    }


    fun sendFeedback(rating:Int, content:String, isSubmitted:Boolean){
        viewModelScope.launch {

            if (!isSubmitted){
                sendFeedbackUseCase(rating, content)
                return@launch
            }

            coroutineContext.job.invokeOnCompletion(completionHandler)
            _uiState.update {
                it.copy(isLoading = true)
            }

            when(val res = sendFeedbackUseCase(rating, content)){
                is Resource.Success -> {
                    val isSendSuccess = res.data
                    if (isSendSuccess){
                        _uiEvent.send(
                            UIEvent.OnUserFeedbackSentEvent
                        )
                        _uiState.update { it.copy(isLoading = false) }
                    }
                }
                is Resource.Failure -> {
                    _uiEvent.send(
                        UIEvent.ToastMessageEvent(res.message)
                    )
                    _uiState.update { it.copy(isLoading = false) }
                }
                is Resource.Error -> {
                    _uiEvent.send(
                        UIEvent.ToastMessageEvent(
                            DynamicString(
                                res.e.message.toString()
                            )
                        )
                    )
                    _uiState.update { it.copy(isLoading = false) }
                }
            }

        }
    }


    init {

        job = viewModelScope.launch(Dispatchers.Default) {
            getCurrentMemberIdUseCase()
                .distinctUntilChanged()
                .onCompletion { cause ->
                    completionHandler.invoke(cause)
                }
                .collectLatest { memberId ->
                    val oldMemberId = _uiState.value.currentMemberAccountId
                    checkInMember(memberId, oldMemberId)
                }
        }

    }

}