package uz.gita.contacts.domain.repository.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse
import uz.gita.contacts.data.model.response.toMessage
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
                        emit(ResultData.Success(this!!))
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        result.errorBody()?.charStream(),
                        ContactResponse::class.java
                    )
                    emit(ResultData.Message(errorResponse.message!!))
                }

            } catch (e: Throwable) {
                emit(ResultData.Error(e))
            }

        }

    override fun login(data: LoginRequest): LiveData<ResultData<TokenResponse>> = liveData {

        try {
            val result = authApi.login(data)
            Log.d("AUTH", "login: ongoing")

            if (result.isSuccessful) {
                Log.d("AUTH", "login: success")

                result.body().apply {
                    pref.isLogedIn = true
                    pref.token = this!!.token.toString()
                    emit(ResultData.Success(this))
                }
            } else {
                val gson = Gson()
                val errorResponse = gson.fromJson(
                    result.errorBody()?.charStream(),
                    ContactResponse::class.java
                )
                Log.d("AUTH", "login:${errorResponse.message!!}")
                emit(ResultData.Message(errorResponse.message!!))
            }

        } catch (e: Throwable) {
            emit(ResultData.Error(e))
        }

    }

    override fun verifySMSCode(data: VerifyRequest): LiveData<ResultData<TokenResponse>> =
        liveData {
            try {
                val result = authApi.verifySmsCode(data)
                if (result.isSuccessful) {
                    result.body().apply {
                        pref.isLogedIn = true
                        pref.token = this!!.token.toString()

                        emit(ResultData.Success(this))
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        result.errorBody()?.charStream(),
                        ContactResponse::class.java
                    )
                    emit(ResultData.Message(errorResponse.message!!))
                }

            } catch (e: Throwable) {
                emit(ResultData.Error(e))
            }
        }
}