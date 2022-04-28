package uz.gita.contacts.data.source.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts_table")
data class ContactEntity(
    @PrimaryKey
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phone: String,
)