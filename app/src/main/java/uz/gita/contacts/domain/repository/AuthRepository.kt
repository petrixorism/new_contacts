package uz.gita.contacts.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse

interface AuthRepository {


    fun register(data: RegisterRequest): LiveData<ResultData<RegisterResponse>>

    fun login(data: LoginRequest): LiveData<ResultData<TokenResponse>>

    fun verifySMSCode(data: VerifyRequest): LiveData<ResultData<TokenResponse>>


}