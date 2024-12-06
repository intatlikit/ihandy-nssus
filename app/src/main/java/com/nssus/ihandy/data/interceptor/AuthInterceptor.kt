package com.nssus.ihandy.data.interceptor

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.constant.AppConstant.APP_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant.PREFIX_TOKEN
import com.nssus.ihandy.data.constant.ValueConstant.REFRESH_TOKEN_API_EXPIRED_FORCE_RESTART_APP_TIME
import com.nssus.ihandy.data.constant.ValueConstant.REQ_HEADER_AUTHORIZATION
import com.nssus.ihandy.data.extension.restartIHandyApp
import com.nssus.ihandy.data.network.exception.UnAuthorizeException
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.runBlocking
import com.nssus.ihandy.data.network.provideOkHttpClientForInterceptor
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.service.UserService
import com.nssus.ihandy.data.util.AppUtil.getCountDownTimer
import okhttp3.logging.HttpLoggingInterceptor
import java.net.HttpURLConnection

class AuthInterceptor(
    private val context: Context,
    private val loggingInterceptor: HttpLoggingInterceptor,
    private val chucker: ChuckerInterceptor,
    private val noConnectionInterceptor: NoConnectionInterceptor
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Set original request variable
        val originalRequest = chain.request()

        // Call Api with original request
        var response = chain.proceed(originalRequest)

        // Check response code equals 401 (access token expired) to attempt token refresh
        println("response.code: ${response.code}")
        if (HttpURLConnection.HTTP_UNAUTHORIZED == response.code) { // HTTP_OK HTTP_UNAUTHORIZED
            // Close the response before making a new request
            response.close() // คอมเม้น ถ้าตอนดัก 200

            // Attempt to refresh the token
            runBlocking {
                // Create Auth Service and call refreshToken Api to get new access token
                val refreshTokenResponse = createAuthService().getUserInfo(
//                    authPrefixToken = "$PREFIX_TOKEN $REFRESH_TOKEN" // make sure that already store REFRESH_TOKEN in login flow
                )

                // Check refreshTokenResponse is successful
                if (refreshTokenResponse.isSuccessful) {
                    // Get refreshTokenResponse data
                    val refreshTokenData = refreshTokenResponse.body()
//                    val newAccessToken = refreshTokenData.accessToken
//                    val newRefreshToken = refreshTokenData.refreshToken
                    println("refreshTokenResponse data: $refreshTokenData")

                    // Store Necessary Token into Global Variable to use anywhere in application
//                    APP_TOKEN = if (newAccessToken.isNullOrEmpty()) APP_TOKEN else newAccessToken
//                    REFRESH_TOKEN = if (newRefreshToken.isNullOrEmpty()) REFRESH_TOKEN else newRefreshToken

                    // Set up the original request with the new token
                    val requestWithNewToken = originalRequest.newBuilder()
                        .header(REQ_HEADER_AUTHORIZATION, "$PREFIX_TOKEN $APP_TOKEN")
                        .build()

                    println("originalRequest: $originalRequest")
                    println("requestWithNewToken: $requestWithNewToken")

                    // Call The Same Api with New-Token Request
                    val responseWithNewToken = chain.proceed(requestWithNewToken)

                    // Return Response
                    println("Check response code condition is Successful")
                    return@runBlocking responseWithNewToken // อาจจะรีเทินปกติไม่ต้อง @ ไม่ก็ reponse = responseWithNewToken
                } else {
                    println("Check response code condition is NOT Successful")
                    // Force Restart App in Specific time
                    Handler(Looper.getMainLooper()).apply {
                        post {
                            getCountDownTimer(
                                countDownTimeInSec = REFRESH_TOKEN_API_EXPIRED_FORCE_RESTART_APP_TIME,
                                onCountDownFinish = { context.restartIHandyApp() }
                            ).apply { start() }
                        }
                    }
                    throw UnAuthorizeException() // Change ur own
                }
            }
            // Retry the request with a new token if available
//            return if (newToken != null) {
//                println("Check new token")
//                // Update APP_TOKEN with new token
//                AppConstant.APP_TOKEN = newToken
//
//                // Retry the original request with the new token
//                val newRequest = originalRequest.newBuilder()
//                    .header("Authorization", "Bearer $newToken")
//                    .build()
//
//                chain.proceed(newRequest)
//            } else {
//            response
            // Return the original response if it's not 401
            //}
        }

        return response
    }

    private fun createAuthService(): UserService {
        val okHttpClient = provideOkHttpClientForInterceptor(
            loggingInterceptor = loggingInterceptor,
            chucker = chucker,
            noConnectionInterceptor = noConnectionInterceptor
        )

        val retrofit = provideRetrofit(
            baseUrl = BuildConfig.BASE_USER, // change ur own path
            okHttpClient = okHttpClient
        )

        return retrofit.create(UserService::class.java) // change ur own service
    }

//    private fun refreshAccessToken(): String? {
//        return runBlocking {
//            // Call the refresh token API and handle the response
//            val result = authenService.refreshToken(
//                authPrefixtoken = "Bearer ${AppConstant.REFRESH_TOKEN}"
//            )
//
//            // Check if the result is a successful response
//            if (result is NetworkResult.Success<*>) {
//                // Check if the data is of type RefreshTokenResponse
//                val responseData = result.data
//                if (responseData is RefreshTokenResponse) {
//                    val newAccessToken = responseData.accessToken
//                    val newRefreshToken = responseData.refreshToken
//
//                    // Update tokens in AppConstant if they are not null or empty
//                    if (!newAccessToken.isNullOrEmpty()) {
//                        AppConstant.APP_TOKEN = newAccessToken
//                    }
//                    if (!newRefreshToken.isNullOrEmpty()) {
//                        AppConstant.REFRESH_TOKEN = newRefreshToken
//                    }
//
//                    return@runBlocking newAccessToken
//                }
//            }
//            null // Token refresh failed
//        }
//    }

}