package id.rla.silungkangplayground.data

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.rla.silungkangplayground.R
import id.rla.silungkangplayground.data.remote.dto.FailedResponse
import id.rla.silungkangplayground.data.remote.dto.Response
import id.rla.silungkangplayground.domain.common.DynamicString
import id.rla.silungkangplayground.domain.common.Resource
import id.rla.silungkangplayground.domain.common.StaticString
import id.rla.silungkangplayground.domain.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseRepository:Repository {
    protected suspend fun <T , S> fetchData(
        gson:Gson,
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
        }catch (e: HttpException){
            val errorResponseBodyString = e.response()?.errorBody()?.string()

            if (errorResponseBodyString?.contains("{") == false){
                return@withContext Resource.Failure(
                    DynamicString(errorResponseBodyString)
                )
            }
            val responseType = object : TypeToken<FailedResponse<Any>>(){}.type
            val errorResponse:FailedResponse<Any> = gson.fromJson(
                errorResponseBodyString,
                responseType
            )
            return@withContext errorResponse.pesan?.let {
                Resource.Failure(
                    DynamicString(it)
                )
            }?: Resource.Failure(
                StaticString(
                    R.string.response_not_success
                )
            )

        }catch (e: Exception) {
            if (e is CancellationException) throw e

            Resource.Error(e)
        }
    }

    protected suspend fun <T:Response<U>, S, U>fetchDataWithCustomHandling(
        gson: Gson,
        fetch: suspend () -> T,
        execute: T.() -> Unit = { },
        executeSuspend: suspend T.() -> Unit = { },
        customSuccessHandling: (suspend T.() -> Resource.Success<S>),
        customFailHandling: (suspend FailedResponse<U>.() -> Resource.Failure<S>)? = null,
    ): Resource<S> = withContext(Dispatchers.IO) {
        try {
            val data = fetch()
            data.execute()
            data.executeSuspend()

            return@withContext customSuccessHandling(data)

        }catch (e: HttpException){
            val errorResponseBodyString = e.response()?.errorBody()?.string()

            val responseType = object : TypeToken<FailedResponse<U>>(){}.type
            val errorResponse:FailedResponse<U> = gson.fromJson(
                errorResponseBodyString,
                responseType
            )

            if (customFailHandling != null){
                return@withContext customFailHandling(errorResponse)
            }


            errorResponse.pesan?.let {
                Resource.Failure(
                    DynamicString(it)
                )
            }?: Resource.Failure(
                StaticString(
                    R.string.response_not_success
                )
            )
        }catch (e: Exception) {
            if (e is CancellationException) throw e

            Resource.Error(e)
        }
    }
}