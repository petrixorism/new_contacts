package uz.gita.contacts.data.source.local.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ContactsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contactEntity: ContactEntity)

    @Delete
    suspend fun deleteContact(contactEntity: ContactEntity)

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): LiveData<List<ContactEntity>>
}