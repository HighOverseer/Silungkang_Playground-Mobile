package id.rla.silungkangplayground.presentation.feature.mainpage.fragment.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.rla.silungkangplayground.domain.usecase.GetEventPlaygroundInPagingUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class EventViewModel @Inject constructor(
    getEventPlaygroundInPagingUseCase: GetEventPlaygroundInPagingUseCase
):ViewModel() {

    val eventPlaygroundsPagings = getEventPlaygroundInPagingUseCase()
        .cachedIn(viewModelScope)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            PagingData.empty()
        )
}