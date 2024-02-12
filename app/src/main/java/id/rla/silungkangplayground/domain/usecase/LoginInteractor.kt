package id.rla.silungkangplayground.domain.usecase

import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import javax.inject.Inject

class LoginInteractor @Inject constructor(
    private val repository: Repository
):LoginUseCase {
    override suspend fun invoke(phoneNumber: String, password: String):Resource<StringRes>{
        if (phoneNumber.isBlank() || password.isBlank()) {
            return Resource.Failure(StaticString(R.string.usename_atau_password_tidak_boleh_kosong))
        }

        return repository.login(phoneNumber.trim(), password.trim())
    }

}