package id.rla.silungkangplayground.domain.usecase

interface ChangeCurrentMemberAccountUseCase {
    suspend operator fun invoke(memberId:String)
}