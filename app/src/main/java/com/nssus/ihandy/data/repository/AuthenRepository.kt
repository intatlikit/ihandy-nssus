package com.nssus.ihandy.data.repository

import com.nssus.ihandy.data.Dispatcher
import com.nssus.ihandy.data.network.baseNetworkFilter
import com.nssus.ihandy.data.service.AuthenService
import com.nssus.ihandy.model.home.UserInfoResponse
import com.nssus.ihandy.model.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

interface AuthenRepository {
//    fun login(req: LoginRequest): Flow<NetworkResult<LoginResponse>>
    //    fun logout(): Flow<NetworkResult<LogoutResponse>>
    //    fun getUserInfo(req: GetUserInfoRequest): Flow<NetworkResult<GetUserInfoResponse>>

}

class AuthenRepositoryImpl(
    private val dispatcher: Dispatcher,
    private val authenSv: AuthenService,
//    private val sharePrefs: SharedPreferences
) : AuthenRepository {
//    override fun login(req: LoginRequest): Flow<NetworkResult<LoginResponse>> = flow {
//        emit(baseNetworkFilter { authenSv.login(req) })
//    }.flowOn(dispatcher.providesDefaultDispatcher())

    //    override fun logout(req): Flow<NetworkResult<LogoutResponse>> = flow {
//        emit(baseNetworkFilter { authenSv.logout(req) })
//    }.flowOn(dispatcher.providesDefaultDispatcher())

    //    override fun getUserInfo(req: GetUserInfoRequest): Flow<NetworkResult<GetUserInfoResponse>> = flow {
//        emit(baseNetworkFilter { authenSv.getUserInfo(req) })
//    }.flowOn(dispatcher.providesDefaultDispatcher())
}