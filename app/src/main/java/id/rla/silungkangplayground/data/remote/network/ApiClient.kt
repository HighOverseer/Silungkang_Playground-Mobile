package id.rla.silungkangplayground.data.remote.network


import id.rla.silungkangplayground.BuildConfig
import id.rla.silungkangplayground.domain.data.UserPreference
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private var CLIENT_INSTANCE:OkHttpClient?=null

    private fun getClient(userPreference: UserPreference):OkHttpClient{
        return CLIENT_INSTANCE ?: synchronized(this){
            CLIENT_INSTANCE ?: OkHttpClient.Builder()
                .addInterceptor(
                    AuthInterceptor(userPreference)
                )
                .build()
        }.also { CLIENT_INSTANCE = it }
    }

    fun getApiService(userPreference:UserPreference): ApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(getClient(userPreference))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}