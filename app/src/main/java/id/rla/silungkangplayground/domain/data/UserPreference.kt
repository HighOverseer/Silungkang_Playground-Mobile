package id.rla.silungkangplayground.domain.data

interface UserPreference {
    suspend fun getToken():String

    suspend fun saveToken(token:String)

    suspend fun resetToken()

    suspend fun getPhoneNumber():String
    suspend fun savePhoneNumber(phoneNumber:String)

    suspend fun resetPhoneNumber()

}