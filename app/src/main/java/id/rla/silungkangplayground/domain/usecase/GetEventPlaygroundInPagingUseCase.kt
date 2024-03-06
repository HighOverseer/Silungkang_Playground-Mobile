package id.rla.silungkangplayground.domain.usecase

import androidx.paging.PagingData
import id.rla.silungkangplayground.domain.model.EventPlayground
import kotlinx.coroutines.flow.Flow

interface GetEventPlaygroundInPagingUseCase {
    operator fun invoke(): Flow<PagingData<EventPlayground>>
}