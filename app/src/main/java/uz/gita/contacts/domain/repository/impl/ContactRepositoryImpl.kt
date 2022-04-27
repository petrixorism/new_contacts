package uz.gita.contacts.domain.repository.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.ContactRequest
import uz.gita.contacts.data.model.response.ContactResponse
import uz.gita.contacts.data.source.api.ContactApi
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.data.source.local.db.ContactsDAO
import uz.gita.contacts.domain.repository.ContactRepository
import uz.gita.contacts.utils.isConnected
import uz.gita.contacts.utils.toContactEntity
import uz.gita.contacts.utils.toContactResponse
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val pref: SharedPref,
    private val contactApi: ContactApi,
    private val contactsDAO: ContactsDAO
) : ContactRepository {
    override fun getAllContacts(): LiveData<ResultData<List<ContactResponse>>> = liveData {

        try {
            if (isConnected()) {
                val result = contactApi.getContactList()
                if (result.isSuccessful) {
                    result.body().apply {
                        contactsDAO.insertAllContacts(this!!.map { it.toContactEntity() })
                    }
                    emit(ResultData.Success(contactsDAO.getAllContacts().value!!.map { it.toContactResponse() }))

                } else {
                    emit(ResultData.Message(result.message()))
                }
            } else {
                emit(ResultData.Success(contactsDAO.getAllContacts().value!!.map { it.toContactResponse() }))
            }

        } catch (e: Throwable) {
            emit(ResultData.Error(e))
        }

    }

    override fun deleteContact(contactRequest: ContactRequest): LiveData<ResultData<ContactResponse>> =
        liveData {
            try {

            } catch (e: Throwable) {
                emit(ResultData.Error(e))
            }
        }

    override fun updateContact(contactResponse: ContactResponse): LiveData<ResultData<ContactResponse>> =
        liveData {
            try {

            } catch (e: Throwable) {
                emit(ResultData.Error(e))
            }
        }

    override fun addContact(id: Int): LiveData<ResultData<ContactResponse>> = liveData {
        try {

        } catch (e: Throwable) {
            emit(ResultData.Error(e))
        }
    }

}