package id.rla.silungkangplayground.domain.data

interface UserPreference {
    suspend fun getToken():String

    suspend fun saveToken(token:String)
}