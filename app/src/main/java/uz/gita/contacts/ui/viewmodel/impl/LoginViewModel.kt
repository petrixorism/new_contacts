package uz.gita.contacts.ui.viewmodel.impl

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.LoginRequest
import uz.gita.contacts.domain.repository.AuthRepository
import uz.gita.contacts.ui.viewmodel.LoginViewModel
import uz.gita.contacts.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class LoginViewModelImpl @Inject constructor(private val repository: AuthRepository):ViewModel(), LoginViewModel{


    override val progressLiveData = MediatorLiveData<Boolean>()
    override val loginLiveData = MutableLiveData<Unit>()
    override val failLiveData = MutableLiveData<String>()
    override val notConnectionLiveData = MutableLiveData<Unit>()

    override fun login(loginRequest: LoginRequest) {
        if (!isConnected()) {
            notConnectionLiveData.postValue(Unit)
        } else {
            viewModelScope.launch {
                progressLiveData.postValue(true)
                val result = repository.login(loginRequest)
                progressLiveData.addSource(result) {

                    when (it) {
                        is ResultData.Success -> {
                            loginLiveData.postValue(Unit)
                        }
                        is ResultData.Message -> {
                            failLiveData.postValue(it.message)
                        }
                        is ResultData.Error -> {
                            failLiveData.postValue(it.error.toString())
                        }
                    }
                }
                progressLiveData.postValue(false)

            }

        }

    }
}