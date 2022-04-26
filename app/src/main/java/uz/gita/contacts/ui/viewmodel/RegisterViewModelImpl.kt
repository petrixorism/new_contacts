package uz.gita.contacts.ui.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.RegisterRequest
import uz.gita.contacts.data.model.request.VerifyRequest
import uz.gita.contacts.data.model.response.RegisterResponse
import uz.gita.contacts.data.model.response.TokenResponse
import uz.gita.contacts.domain.repository.AuthRepository
import uz.gita.contacts.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class RegisterViewModelImpl @Inject constructor(private val repository: AuthRepository) :
    ViewModel(),
    RegisterViewModel {

    override val progressLiveDataScope = MediatorLiveData<Boolean>()
    override val registrationLiveData = MutableLiveData<RegisterResponse>()
    override val failLiveData = MutableLiveData<String>()
    override val notConnectionLiveData = MutableLiveData<Boolean>()
    override val verifyLiveData = MutableLiveData<TokenResponse>()

    override fun register(registerRequest: RegisterRequest) {

        if (!isConnected()) {
            notConnectionLiveData.postValue(true)
        } else {
            viewModelScope.launch {
                progressLiveDataScope.postValue(true)
                val result = repository.register(registerRequest)
                progressLiveDataScope.addSource(result) {

                    when (it) {
                        is ResultData.Success -> {
                            registrationLiveData.postValue(it.data!!)
                        }
                        is ResultData.Message -> {
                            failLiveData.postValue(it.message)
                        }
                        is ResultData.Error -> {
                            failLiveData.postValue(it.error!!.toString())
                        }
                    }

                }
            }

        }

    }

    override fun verify(data: VerifyRequest) {
        if (!isConnected()) {
            notConnectionLiveData.postValue(true)
        } else {
            viewModelScope.launch {
                progressLiveDataScope.postValue(true)
                val result = repository.verifySMSCode(data)
                progressLiveDataScope.addSource(result) {

                    when (it) {
                        is ResultData.Success -> {
                            verifyLiveData.postValue(it.data!!)
                        }
                        is ResultData.Message -> {
                            failLiveData.postValue(it.message)
                        }
                        is ResultData.Error -> {
                            failLiveData.postValue(it.error!!.toString())
                        }
                    }

                }
            }

        }
    }


}