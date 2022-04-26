package uz.gita.contacts.ui.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse

interface RegisterViewModel {

    val progressLiveDataScope: LiveData<Boolean>
    val registrationLiveData: LiveData<RegisterResponse>
    val failLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<Boolean>
    val verifyLiveData: LiveData<TokenResponse>

    fun register(registerRequest: RegisterRequest)

    fun verify(data: VerifyRequest)

}