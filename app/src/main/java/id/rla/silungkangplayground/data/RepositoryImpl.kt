package id.rla.silungkangplayground.data

import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.data.remote.network.ApiService
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.Repository
import id.rla.silungkangplayground.domain.data.UserPreference
import id.rla.silungkangplayground.domain.helper.Mapper
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
):Repository {

/*
    private suspend fun <T, S> fetchData(
        fetch: suspend () -> Call<T>,
        mapData: suspend T.() -> S,
        execute: T.() -> Unit = { },
        executeSuspend: suspend T.() -> Unit = { },
    ): Resource<S> = withContext(Dispatchers.IO) {
        try {
            val response = fetch().awaitResponse()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                responseBody.execute()
                responseBody.executeSuspend()

                val result = withContext(Dispatchers.Default){
                    mapData(responseBody)
                }

                Resource.Success(
                    result
                )
            } else {
                response.errorBody()?.string()?.let {
                    Resource.Failure(
                        DynamicString(it)
                    )
                } ?: Resource.Failure(
                    StaticString(
                        R.string.response_not_success
                    )
                )
            }
        }catch (e: Exception) {
            if (e is CancellationException) throw e

            Resource.Error(e)
        }
    }
*/


    private suspend fun <T, S> fetchData(
        fetch: suspend () -> T,
        mapData: suspend T.() -> S,
        execute: T.() -> Unit = { },
        executeSuspend: suspend T.() -> Unit = { },
    ): Resource<S> = withContext(Dispatchers.IO) {
        try {
            val data = fetch()
            data.execute()
            data.executeSuspend()

            val result = mapData(data)

            Resource.Success(
                result
            )
        }catch (e:HttpException){
            e.response()?.errorBody()?.string()?.let {
                Resource.Failure(
                    DynamicString(it)
                )
            }?:Resource.Failure(
                StaticString(
                    R.string.response_not_success
                )
            )
        }catch (e: Exception) {
            if (e is CancellationException) throw e

            Resource.Error(e)
        }
    }


    override suspend fun login(phoneNumber: String, password: String): Resource<StringRes> {
        return fetchData(
            fetch = {
                apiService.login(phoneNumber, password)
            },
            executeSuspend = {
                withContext(NonCancellable){
                    if (token.isNullOrBlank()) throw Exception("Token not found..")

                    listOf(
                        launch { userPreference.saveToken(token) },
                        launch { userPreference.savePhoneNumber(phoneNumber) }
                    ).joinAll()
                }

            },
            mapData = {
                val message = message ?: "Login Berhasil!"
                DynamicString(message)
            }
        )
    }

    override suspend fun getDetailMemberVoucher(): Resource<MemberVoucherInfo> {
         return fetchData(
             fetch = {
                 val memberId = userPreference.getPhoneNumber()
                 apiService.getMemberVoucherInfo(memberId)
             },
             mapData = {
                 Mapper.mapMemberVoucherInfoDtoToDomain(
                     this
                 )
             }
         )
    }


    override suspend fun getMemberHistory(): Resource<List<MemberHistoryItem>>{
        return fetchData(
            fetch = {
                val memberId = userPreference.getPhoneNumber()
                apiService.getMemberHistory(memberId)
            },
            mapData = {
                Mapper.mapMemberHistoryDtoToDomain(this)
            }
        )
    }
}