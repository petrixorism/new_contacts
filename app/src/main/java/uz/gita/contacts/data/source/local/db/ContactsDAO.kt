package uz.gita.contacts.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContact(contactEntity: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllContacts(contactEntity: List<ContactEntity>)

    @Update
    suspend fun updateContact(contactEntity: ContactEntity)

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): LiveData<List<ContactEntity>>
}