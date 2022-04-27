package uz.gita.contacts.ui.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse

interface LoginViewModel {


    val progressLiveData: LiveData<Boolean>
    val loginLiveData: LiveData<Unit>
    val failLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<Unit>

    fun login(loginRequest: LoginRequest)

}