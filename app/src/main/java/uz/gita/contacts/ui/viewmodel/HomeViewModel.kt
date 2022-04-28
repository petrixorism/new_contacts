package uz.gita.contacts.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import uz.gita.contacts.data.model.request.AddContactRequest
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse

interface HomeViewModel {

    val getAllContactsLiveData: LiveData<List<ContactResponse>>
    val progressLiveData: MediatorLiveData<Boolean>
    val deleteContactLiveData: LiveData<ContactResponse>
    val addContactLiveData: LiveData<ContactResponse>
    val updateContactLiveData: LiveData<ContactResponse>
    val failLiveData: LiveData<String>
    val notConnectionLiveData: LiveData<Unit>
    val logoutLiveData:LiveData<Unit>

    fun getAllContacts()

    fun addContact(contactRequest: AddContactRequest)

    fun updateContact(contactRequest: ContactRequest)

    fun deleteContact(id: Int)

    fun logout()
}