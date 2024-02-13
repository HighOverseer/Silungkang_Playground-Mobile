package id.rla.silungkangplayground.domain.data

interface UserPreference {
    suspend fun getToken():String
    suspend fun saveToken(token:String)
    suspend fun resetToken()
    suspend fun getPhoneId():Int
    suspend fun savePhoneId(phoneId:Int)
    suspend fun resetPhoneId()

}