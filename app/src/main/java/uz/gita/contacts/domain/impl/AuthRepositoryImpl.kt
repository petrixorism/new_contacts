package uz.gita.contacts.domain.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse
import uz.gita.contacts.data.source.api.AuthApi
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.data.source.local.db.ContactsDAO
import uz.gita.contacts.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl
@Inject constructor(
    private val authApi: AuthApi,
    private val contactsDAO: ContactsDAO,
    private val pref: SharedPref
) : AuthRepository {

    override fun register(data: RegisterRequest): LiveData<ResultData<RegisterResponse>> =
        liveData {

            try {
                val result = authApi.register(data)
                if (result.isSuccessful) {
                    result.body().apply {
                        Log.d("CNCT","Success ${this?.message!!}")
                        emit(ResultData.Success<RegisterResponse>(this.data!!))
                    }
                } else {
                    Log.d("CNCT","Fail ${result.body()!!.message}")
                    emit(ResultData.Message<RegisterResponse>(result.message()))
                }

            } catch (e: Throwable) {
                Log.d("CNCT","Error ${e.message}")
                emit(ResultData.Error<RegisterResponse>(e))
            }

        }

    override fun login(data: LoginRequest): LiveData<ResultData<TokenResponse>> = liveData{

        try {
            val result = authApi.login(data)
            if (result.isSuccessful) {
                result.body().apply {
                    emit(ResultData.Success<TokenResponse>(this!!.data!!))
                }
            } else {
                emit(ResultData.Message<TokenResponse>(result.message()))
            }

        } catch (e: Throwable) {
            emit(ResultData.Error<TokenResponse>(e))
        }

    }

    override fun verifySMSCode(data: VerifyRequest): LiveData<ResultData<TokenResponse>> = liveData {
        try {
            val result = authApi.verifySmsCode(data)
            if (result.isSuccessful) {
                result.body().apply {
                    emit(ResultData.Success<TokenResponse>(this!!.data!!))
                }
            } else {
                emit(ResultData.Message<TokenResponse>(result.message()))
            }

        } catch (e: Throwable) {
            emit(ResultData.Error<TokenResponse>(e))
        }
    }
}