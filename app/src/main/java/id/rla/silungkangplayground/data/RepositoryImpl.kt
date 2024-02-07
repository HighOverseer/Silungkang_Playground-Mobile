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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.awaitResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
):Repository {

    private suspend fun <T, S> fetchData(
        fetch: suspend () -> Call<T>,
        execute: T.() -> Unit = { },
        executeSuspend: suspend T.() -> Unit = { },
        mapData: T.() -> S,
    ): Resource<S> = withContext(Dispatchers.IO) {
        try {
            val response = fetch().awaitResponse()
            val responseBody = response.body()
            if (response.isSuccessful && responseBody != null) {
                responseBody.execute()
                responseBody.executeSuspend()

                Resource.Success(
                    mapData(responseBody)
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
        } catch (e: HttpException) {
            Resource.Error(e)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun login(memberId: String, password: String): Resource<StringRes>
     = withContext(Dispatchers.Default){
        fetchData(
            fetch = {
                apiService.login(memberId, password)
            },
            executeSuspend = {
                if (token.isNullOrBlank()) throw Exception("Token not found..")

                userPreference.saveToken(token)
                userPreference.saveMemberId(memberId)
            },
            mapData = {
                DynamicString(message.toString())
            }
        )
    }

    override suspend fun getDetailMemberVoucher(): Resource<MemberVoucherInfo>
     = withContext(Dispatchers.Default){
         fetchData(
             fetch = {
                 val memberId = userPreference.getMemberId()
                 apiService.getMemberVoucherInfo(memberId)
             },
             mapData = {
                 Mapper.mapMemberVoucherInfoDtoToDomain(
                     this
                 )
             }
         )
    }


    override suspend fun getMemberHistory(): Resource<List<MemberHistoryItem>>
    = withContext(Dispatchers.Default){
        fetchData(
            fetch = {
                val memberId = userPreference.getMemberId()
                apiService.getMemberHistory(memberId)
            },
            mapData = {
                Mapper.mapMemberHistoryDtoToDomain(this)
            }
        )
    }

    companion object{
        @Volatile
        private var INSTANCE:RepositoryImpl?=null

        fun getInstance(
            apiService: ApiService,
            userPreference: UserPreference
        ):RepositoryImpl{
            return INSTANCE?: synchronized(this){
                INSTANCE?:RepositoryImpl(apiService, userPreference)
            }.also { INSTANCE = it }
        }
    }
}