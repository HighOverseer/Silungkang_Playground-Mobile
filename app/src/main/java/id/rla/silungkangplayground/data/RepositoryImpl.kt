package id.rla.silungkangplayground.data

import com.google.gson.Gson
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.data.remote.network.ApiService
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StringRes
import id.rla.silungkangplayground.domain.data.UserPreference
import id.rla.silungkangplayground.domain.helper.Mapper
import id.rla.silungkangplayground.domain.model.MemberHistoryItem
import id.rla.silungkangplayground.domain.model.MemberVoucherInfo
import id.rla.silungkangplayground.data.helper.QrCodeGenerator
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.model.CardMember
import id.rla.silungkangplayground.domain.model.CekInData
import id.rla.silungkangplayground.domain.model.MemberAccount
import id.rla.silungkangplayground.domain.model.OfferedVoucher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.job
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext

@Singleton
class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference,
    private val qrCodeGenerator: QrCodeGenerator,
    private val gson: Gson,
):BaseRepository(){

    override suspend fun login(phoneNumber: String, password: String): Resource<StringRes> {
        return fetchData(
            gson = gson,
            fetch = {
                apiService.login(phoneNumber, password)
            },
            executeSuspend = {
                withContext(NonCancellable){

                    val token = data?.token ?: throw Exception("Login is not successful")
                    val phoneId = data.phoneId ?: throw Exception("Login is not successful")
                    val defaultMemberId = data.defaultMemberId ?: throw Exception("Login is not successful")

                    listOf(
                        launch { userPreference.saveToken(token) },
                        launch { userPreference.savePhoneId(phoneId) },
                        launch { userPreference.saveCurrentMemberId(defaultMemberId) }
                    ).joinAll()
                }
            },
            mapData = {
                val message = message ?: "Login Berhasil!"
                DynamicString(message)
            }
        )
    }

    override fun getCurrentMemberId(): Flow<String> = userPreference.getCurrentMemberId()

    override suspend fun getDetailMemberVoucher(): Resource<MemberVoucherInfo> {
         return fetchData(
             gson = gson,
             fetch = {
                 val phoneId = userPreference.getPhoneId()
                 apiService.getMemberVoucherInfo(phoneId)
             },
             mapData = {
                 Mapper.mapMemberVoucherInfoDtoToDomain(
                     this.data
                 )
             }
         )
    }

    override suspend fun checkInMember(memberId:String): Resource<CekInData> {
        return fetchDataWithCustomHandling(
            gson = gson,
            fetch = {
                val phoneId = userPreference.getPhoneId()
                apiService.checkInMember(phoneId, memberId)
            },
            customFailHandling = {
                val message = DynamicString(pesan?:"Terjadi Kesalahan..")
                rows ?: return@fetchDataWithCustomHandling Resource.Failure(message)
                val list = Mapper.mapMemberAccountDtoToDomain(rows.listMember)
                Resource.Failure(
                    message = message,
                    data = CekInData(list, false)
                )
            },
            customSuccessHandling = {
                val list = Mapper.mapMemberAccountDtoToDomain(data?.listMember)
                Resource.Success(
                    data = CekInData(list, data?.sendFeedbackEvent?:false),
                    message = DynamicString(message ?: "Terjadi Kesalahan..")
                )
            }
        )
    }

    override suspend fun getMemberHistory(): Resource<List<MemberHistoryItem>>{
        return fetchData(
            gson = gson,
            fetch = {
                val phoneId = userPreference.getPhoneId()
                apiService.getMemberHistory(phoneId)
            },
            mapData = {
                Mapper.mapMemberHistoryDtoToDomain(this.data)
            }
        )
    }

    override suspend fun getCardMember(): Resource<List<CardMember>> {
        return fetchData(
            gson = gson,
            fetch = {
                val phoneId = userPreference.getPhoneId()
                apiService.getCardMember(phoneId)
            },
            mapData = {
                Mapper.mapCardMemberDtoToDomain(qrCodeGenerator, data)
            }
        )
    }
    override suspend fun changeCurrentMemberAccount(memberId: String) {
        userPreference.saveCurrentMemberId(memberId)
    }
    override suspend fun logout(): Resource<Unit> {
        return withContext(NonCancellable){
            userPreference.apply {
                listOf(
                    launch { resetToken() },
                    launch { resetPhoneId() },
                    launch { resetCurrentMemberId() }
                ).joinAll()
            }

            Resource.Success(
                    Unit,
                    StaticString(R.string.anda_telah_logout)
                )
        }
    }
    override suspend fun getOfferedVouchers(): Resource<List<OfferedVoucher>> {
        return fetchData(
            gson = gson,
            fetch = {
                apiService.getVouchers()
            },
            mapData = {
                Mapper.mapOfferedVoucherDtoToDomain(data)
            }
        )
    }
    override suspend fun exchangePoint(
        voucherTypeId: Int
    ): Resource<StringRes> {
        return fetchData(
            gson = gson,
            fetch = {
                val phoneId = userPreference.getPhoneId()
                val memberId = userPreference.getCurrentMemberId().first()
                apiService.exchangePoint(phoneId, memberId, voucherTypeId)
            },
            mapData = {
                if (message == null) throw Exception("Maaf, Terjadi Kesalahan..")
                DynamicString(message)
            }
        )
    }
    override fun isUserHasAlreadyLoggin(): Flow<Boolean> {
        return userPreference.isUserHasAlreadyLoggedIn()
    }
    override suspend fun sendFeedback(rating: Int, content: String): Resource<Boolean> {
        return fetchDataWithCustomHandling(
            gson = gson,
            fetch = {
                val phoneId = userPreference.getPhoneId()
                apiService.sendFeedback(phoneId, rating, content)
            },
            customSuccessHandling = {
                Resource.Success(data ?: false)
            }
        )
    }
}