package id.rla.silungkangplayground.domain.usecase.interactor

import androidx.paging.PagingData
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.EventPlayground
import id.rla.silungkangplayground.domain.usecase.GetEventPlaygroundInPagingUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventPlaygroundInPagingInteractor @Inject constructor(
    private val repository: Repository
):GetEventPlaygroundInPagingUseCase {
    override fun invoke(): Flow<PagingData<EventPlayground>> {
        return repository.getEventPlaygroundInPaging()
    }
}