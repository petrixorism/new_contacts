package uz.gita.contacts.data.source.api

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.gita.contacts.data.model.MainResponse
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse

interface AuthApi {

    @POST("/api/v1/register")
    suspend fun register(@Body data: RegisterRequest): Response<MainResponse<RegisterResponse>>

    @POST("/api/v1/register/verify")
    suspend fun verifySmsCode(@Body data: VerifyRequest): Response<MainResponse<TokenResponse>>

    @POST("/api/v1/login")
    suspend fun login(@Body data: LoginRequest): Response<MainResponse<TokenResponse>>


}