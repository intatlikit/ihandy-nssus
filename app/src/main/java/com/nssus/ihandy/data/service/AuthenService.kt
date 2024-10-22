package com.nssus.ihandy.data.service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenService {
//    @POST("login")
//    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
//
//        @GET("refreshToken") // ดูว่าใช้พาท auth หรือ authen ถ้าใช้ auth ต้องแยกอีกservice
//    suspend fun refreshToken(): Response<LoginResponse>

    //    @POST("logout")
//    suspend fun logout(): Response<LogoutResponse> // อาจไม่ต้องมีเรสปอนโมเดล

    //    @GET("user") // อาจไม่ต้องมี request
//    suspend fun getUserInfo(@Body request: GetUserInfoRequest): Response<GetUserInfoResponse>
}