package uz.gita.contacts.domain.repository.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import uz.gita.contacts.data.model.ResultData
import uz.gita.contacts.data.model.request.AddContactRequest
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

private val TAG = "CONTACT_REPOSITORY"

class ContactRepositoryImpl @Inject constructor(
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
                    emit(
                        ResultData.Success(
                            contactsDAO.getAllContacts().map { it.toContactResponse() })
                    )
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        result.errorBody()?.charStream(),
                        ContactResponse::class.java
                    )

                    emit(
                        ResultData.Message(
                            errorResponse.message.toString()
                        )
                    )
                }
            } else {
                emit(
                    ResultData.Success(
                        contactsDAO.getAllContacts().map { it.toContactResponse() })
                )
            }

        } catch (e: Throwable) {
            emit(ResultData.Error(e))
        }

    }

    override fun addContact(contactRequest: AddContactRequest): LiveData<ResultData<ContactResponse>> =
        liveData {
            try {
                val result = contactApi.addContact(contactRequest)
                if (result.isSuccessful) {
                    Log.d(TAG, "addContact: SUCCESS 1")
                    result.body().apply {
                        contactsDAO.insertContact(this!!.toContactEntity())
                        Log.d(TAG, "addContact: SUCCESS 2")
                        emit(ResultData.Success(this))
                    }
                } else {
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        result.errorBody()?.charStream(),
                        ContactResponse::class.java
                    )
                    Log.d(TAG, "addContact: FAIL")
                    emit(ResultData.Message(errorResponse.message.toString()))
                }
            } catch (e: Throwable) {
                Log.d(TAG, "addContact: ERROR")
                emit(ResultData.Error(e))
            }
        }

    override fun updateContact(contactRequest: ContactRequest): LiveData<ResultData<ContactResponse>> =
        liveData {
            try {
                val result = contactApi.editContact(contactRequest)
                if (result.isSuccessful) {
                    result.body().apply {
                        contactsDAO.updateContact(this!!.toContactEntity())
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

    override fun deleteContact(id: Int): LiveData<ResultData<ContactResponse>> = liveData {
        try {
            val result = contactApi.deleteContact(id)
            if (result.isSuccessful) {
                result.body().apply {
                    contactsDAO.deleteContact(this!!.toContactEntity())
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