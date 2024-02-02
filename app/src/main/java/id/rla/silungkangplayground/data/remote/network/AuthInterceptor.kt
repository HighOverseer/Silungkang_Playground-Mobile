package id.rla.silungkangplayground.data.remote.network

import id.rla.silungkangplayground.domain.data.UserPreference
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val userPreference: UserPreference):Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var token:String
        return runBlocking {
            token = userPreference.getToken()
            if (token.isNotEmpty()){
                val authorized = original.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
                return@runBlocking chain.proceed(authorized)
            }
            chain.proceed(original)
        }
    }
}