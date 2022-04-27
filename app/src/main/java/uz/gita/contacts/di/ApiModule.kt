package uz.gita.contacts.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import uz.gita.contacts.data.source.api.AuthApi
import uz.gita.contacts.data.source.api.ContactApi
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @[Provides Singleton]
    fun provideNewsApi(retrofit: Retrofit): AuthApi = retrofit.create(AuthApi::class.java)

    @[Provides Singleton]
    fun provideContactApi(retrofit: Retrofit): ContactApi = retrofit.create(ContactApi::class.java)

}