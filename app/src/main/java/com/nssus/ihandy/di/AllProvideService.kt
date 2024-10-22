package com.nssus.ihandy.di

import com.nssus.ihandy.BuildConfig
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.service.AuthenService
import com.nssus.ihandy.data.service.UserService
import okhttp3.OkHttpClient

// comment this if not use
fun provideUserService(okHttpClient: OkHttpClient): UserService =
    provideRetrofit(
        baseUrl = BuildConfig.BASE_USER,
        okHttpClient = okHttpClient
    ).create(UserService::class.java)

// un comment this
//fun provideAuthenService(okHttpClient: OkHttpClient): AuthenService =
//    provideRetrofit(
//        baseUrl = BuildConfig.BASE_AUTHEN,
//        okHttpClient = okHttpClient
//    ).create(AuthenService::class.java)