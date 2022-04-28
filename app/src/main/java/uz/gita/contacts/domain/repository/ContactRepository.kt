package uz.gita.contacts.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.AddContactRequest
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse

interface ContactRepository {

    fun getAllContacts(): LiveData<ResultData<List<ContactResponse>>>

    fun addContact(contactRequest: AddContactRequest): LiveData<ResultData<ContactResponse>>

    fun updateContact(contactRequest: ContactRequest): LiveData<ResultData<ContactResponse>>

    fun deleteContact(id: Int): LiveData<ResultData<ContactResponse>>
}