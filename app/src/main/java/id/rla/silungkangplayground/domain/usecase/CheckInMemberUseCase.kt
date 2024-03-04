package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.model.CekInData
import id.rla.silungkangplayground.domain.model.MemberAccount

interface CheckInMemberUseCase {
    suspend operator fun invoke(memberId:String): Resource<CekInData>

}