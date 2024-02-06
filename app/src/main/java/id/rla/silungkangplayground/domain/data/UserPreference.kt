package id.rla.silungkangplayground.domain.data

interface UserPreference {
    suspend fun getToken():String

    suspend fun saveToken(token:String)

    suspend fun resetToken()

    suspend fun getMemberId():String
    suspend fun saveMemberId(memberId:String)

    suspend fun resetMemberId()

}