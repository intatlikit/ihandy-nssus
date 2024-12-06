package com.nssus.ihandy.di

import com.nssus.ihandy.data.interceptor.AuthInterceptor
import com.nssus.ihandy.data.interceptor.Chucker
import com.nssus.ihandy.data.interceptor.NoConnectionInterceptor
import com.nssus.ihandy.data.interceptor.RequestInterceptor
import com.nssus.ihandy.data.network.provideLoggingInterceptor
import com.nssus.ihandy.data.network.provideOkHttpClient
import com.nssus.ihandy.data.network.provideRetrofit
import com.nssus.ihandy.data.repository.UserRepository
import com.nssus.ihandy.data.repository.UserRepositoryImpl
import com.nssus.ihandy.data.usecase.HomeUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule = module {
    // Interceptor
    single(named("chucker")) { Chucker.createChucker() }
    single { NoConnectionInterceptor(get()) }
    single { RequestInterceptor() } // get()
    single { provideLoggingInterceptor() }
    single { AuthInterceptor(get(), get(), get(named("chucker")), get()) }

    // Network
    single { provideRetrofit(get(), get(), get()) }
    single { provideOkHttpClient(get(), get(named("chucker")), get(), get(), get()) }

    // Service & Repository
    single { provideUserService(get()) } // comment this if not use
    single<UserRepository> { UserRepositoryImpl(get(), get()) } // comment this if not use
//    single { provideAuthenService(get()) } // uncomment this
//    single<AuthenRepository> { AuthenRepositoryImpl(get(), get()) } // uncomment this

    // UseCase
//    single { AuthenUseCase(get(), get()) } // uncomment this
    single { HomeUseCase(get(), get()) } // comment this
}