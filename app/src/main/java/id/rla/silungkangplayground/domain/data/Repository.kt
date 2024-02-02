package id.rla.silungkangplayground.domain.data

import id.rla.silungkangplayground.domain.core.Resource
import id.rla.silungkangplayground.domain.core.StringRes

interface Repository {

    suspend fun login(
        memberId: String,
        password: String
    ): Resource<StringRes>


}