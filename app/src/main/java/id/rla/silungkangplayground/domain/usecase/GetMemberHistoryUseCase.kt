package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.model.MemberHistoryItem

interface GetMemberHistoryUseCase {
    suspend operator fun invoke():Resource<List<MemberHistoryItem>>

}