package id.rla.silungkangplayground.domain.usecase.interactor

import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.model.CekInData
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.domain.usecase.CheckInMemberUseCase
import javax.inject.Inject

class CheckInMemberInteractor @Inject constructor(
    private val repository: Repository
) : CheckInMemberUseCase {
    override suspend fun invoke(
        memberId: String,
    ): Resource<CekInData> {
        if (memberId.isEmpty()) {
            return Resource.Failure(
                StaticString(
                    R.string.maaf_terjadi_kesalahan
                )
            )
        }

        return repository.checkInMember(memberId)
    }
}