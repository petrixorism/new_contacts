package uz.gita.contacts.ui.viewmodel.impl

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.AddContactRequest
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.domain.repository.ContactRepository
import uz.gita.contacts.ui.viewmodel.HomeViewModel
import uz.gita.contacts.utils.isConnected
import javax.inject.Inject

@HiltViewModel
class HomeViewModelImpl @Inject constructor(private val repository: ContactRepository) :
    ViewModel(),
    HomeViewModel {

    override val getAllContactsLiveData = MutableLiveData<List<ContactResponse>>()
    override val progressLiveData = MediatorLiveData<Boolean>()
    override val deleteContactLiveData = MutableLiveData<ContactResponse>()
    override val addContactLiveData = MutableLiveData<ContactResponse>()
    override val updateContactLiveData = MutableLiveData<ContactResponse>()
    override val failLiveData = MutableLiveData<String>()
    override val notConnectionLiveData = MutableLiveData<Unit>()
    override val logoutLiveData = MutableLiveData<Unit>()

    override fun getAllContacts() {
        progressLiveData.postValue(true)
        if (!isConnected()) {
            notConnectionLiveData.postValue(Unit)
        }
        viewModelScope.launch {

            progressLiveData.addSource(repository.getAllContacts()) {
                when (it) {
                    is ResultData.Success -> getAllContactsLiveData.postValue(it.data!!)
                    is ResultData.Message -> failLiveData.postValue(it.message)
                    is ResultData.Error -> failLiveData.postValue(it.error.message)
                }

            }
            progressLiveData.postValue(true)
            progressLiveData.postValue(false)

        }
    }

    override fun addContact(contactRequest: AddContactRequest) {
        progressLiveData.postValue(true)
        if (!isConnected()) {
            notConnectionLiveData.postValue(Unit)
        }
        viewModelScope.launch {
            progressLiveData.addSource(repository.addContact(contactRequest)) {
                when (it) {
                    is ResultData.Success -> addContactLiveData.postValue(it.data!!)
                    is ResultData.Message -> failLiveData.postValue(it.message)
                    is ResultData.Error -> failLiveData.postValue(it.error.message)
                }
            }
        }
        progressLiveData.postValue(false)
    }

    override fun updateContact(contactRequest: ContactRequest) {
        progressLiveData.postValue(true)
        if (!isConnected()) {
            notConnectionLiveData.postValue(Unit)
        }
        viewModelScope.launch {
            progressLiveData.addSource(repository.updateContact(contactRequest)) {
                when (it) {
                    is ResultData.Success -> addContactLiveData.postValue(it.data!!)
                    is ResultData.Message -> failLiveData.postValue(it.message)
                    is ResultData.Error -> failLiveData.postValue(it.error.message)
                }
            }
        }
        progressLiveData.postValue(false)
    }

    override fun deleteContact(id: Int) {
        progressLiveData.postValue(true)
        if (!isConnected()) {
            notConnectionLiveData.postValue(Unit)
        }
        viewModelScope.launch {
            progressLiveData.addSource(repository.deleteContact(id)) {
                when (it) {
                    is ResultData.Success -> updateContactLiveData.postValue(it.data!!)
                    is ResultData.Message -> failLiveData.postValue(it.message)
                    is ResultData.Error -> failLiveData.postValue(it.error.message)
                }
            }
        }
        progressLiveData.postValue(false)
    }

    override fun logout() {
        SharedPref.getInstance().apply {
            token = ""
            isLogedIn = false
        }
        logoutLiveData.postValue(Unit)
    }
}