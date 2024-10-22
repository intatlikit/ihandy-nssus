package com.nssus.ihandy.data.network

import com.nssus.ihandy.data.constant.ValueConstant.STATUS_CODE_NO_INTERNET
import com.nssus.ihandy.data.constant.LanguageConstant.BASE_NO_INTERNET
import com.nssus.ihandy.data.constant.LanguageConstant.BASE_SOCKET_TIME_OUT
import com.nssus.ihandy.data.constant.LanguageConstant.BASE_TOKEN_EXPIRED
import com.nssus.ihandy.data.constant.ValueConstant.STATUS_CODE_ERROR
import com.nssus.ihandy.data.constant.ValueConstant.STATUS_CODE_SOCKET_TIME_OUT
import com.nssus.ihandy.data.constant.ValueConstant.STATUS_CODE_TOKEN_EXPIRED
import com.nssus.ihandy.data.network.exception.NoConnectivityException
import com.nssus.ihandy.data.network.exception.UnAuthorizeException
import com.nssus.ihandy.model.network.NetworkResult
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

internal suspend fun <T> baseNetworkFilter(call: suspend () -> Response<T>): NetworkResult<T> {
    try {
        val response = call()
        val statusCode = response.code()

        return if (response.isSuccessful) {
            val responseBody = response.body()
            NetworkResult.Success(data = responseBody, statusCode = statusCode.toString())
        } else {
            NetworkResult.Error(errorMessage = response.message(), statusCode = statusCode.toString())
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        return when (t) {
            is NoConnectivityException, is UnknownHostException ->
                NetworkResult.Error(errorMessage = BASE_NO_INTERNET, statusCode = STATUS_CODE_NO_INTERNET)
            is SocketTimeoutException ->
                NetworkResult.Error(errorMessage = BASE_SOCKET_TIME_OUT, statusCode = STATUS_CODE_SOCKET_TIME_OUT)
            is UnAuthorizeException ->
                NetworkResult.Error(errorMessage = BASE_TOKEN_EXPIRED, statusCode = STATUS_CODE_TOKEN_EXPIRED)
            else -> NetworkResult.Error(errorMessage = t.message, statusCode = STATUS_CODE_ERROR)
        }
    }
}