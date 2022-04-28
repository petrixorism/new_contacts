package uz.gita.contacts.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.contacts.data.source.local.SharedPref
import uz.gita.contacts.data.source.local.db.ContactDatabase
import uz.gita.contacts.data.source.local.db.ContactsDAO
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @[Provides Singleton]
    fun provideContactDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ContactDatabase::class.java, "contact_db").allowMainThreadQueries().build()

    @[Provides Singleton]
    fun provideContactDao(db: ContactDatabase): ContactsDAO = db.getContactDao()

    @[Provides Singleton]
    fun provideSharedPref() = SharedPref.getInstance()
}