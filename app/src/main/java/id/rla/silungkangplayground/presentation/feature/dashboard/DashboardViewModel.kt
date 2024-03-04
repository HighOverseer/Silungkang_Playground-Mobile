package id.rla.silungkangplayground.presentation.feature.dashboard

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.usecase.CheckIsUserHasAlreadyLoggedInUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    checkIsUserHasAlreadyLoggedInUseCase: CheckIsUserHasAlreadyLoggedInUseCase
):ViewModel() {

    var isUserHasAlreadyLoggedIn:Boolean? = null
        private set



    init {
        viewModelScope.launch {
            checkIsUserHasAlreadyLoggedInUseCase()
                .distinctUntilChanged()
                .collectLatest {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){
                        delay(1000)
                    }
                    isUserHasAlreadyLoggedIn = it
            }
        }
    }

}