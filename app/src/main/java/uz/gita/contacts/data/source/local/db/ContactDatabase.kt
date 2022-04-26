package uz.gita.contacts.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [ContactEntity::class],
    version = 1
)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactsDAO
}