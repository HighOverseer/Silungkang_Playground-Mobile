package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.usecase.GetMemberHistoryUseCase
import javax.inject.Inject


class GetMemberHistoryInteractor @Inject constructor(
    private val repository: Repository
): GetMemberHistoryUseCase {
    override suspend fun invoke(): Resource<List<MemberHistoryItem>> {
        return repository.getMemberHistory()
    }
}