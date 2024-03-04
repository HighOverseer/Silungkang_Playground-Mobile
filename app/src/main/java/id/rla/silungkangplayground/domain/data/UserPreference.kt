package id.rla.silungkangplayground.domain.data

import kotlinx.coroutines.flow.Flow

interface UserPreference {
    suspend fun getToken():String
    suspend fun saveToken(token:String)
    suspend fun resetToken()
    suspend fun getPhoneId():Int
    suspend fun savePhoneId(phoneId:Int)
    suspend fun resetPhoneId()

    fun getCurrentMemberId(): Flow<String>
    suspend fun saveCurrentMemberId(memberId:String)
    suspend fun resetCurrentMemberId()

    fun isUserHasAlreadyLoggedIn():Flow<Boolean>

}