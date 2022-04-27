package uz.gita.contacts.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse

interface ContactRepository {

    fun getAllContacts(): LiveData<List<ResultData<ContactResponse>>>

    fun deleteContact(contactRequest: ContactRequest): LiveData<ResultData<ContactResponse>>

    fun updateContact(contactResponse: ContactResponse): LiveData<ResultData<ContactResponse>>

    fun addContact(id: Int): LiveData<ResultData<ContactResponse>>
}