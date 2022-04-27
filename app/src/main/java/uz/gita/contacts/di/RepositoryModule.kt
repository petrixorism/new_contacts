package uz.gita.contacts.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.contacts.domain.repository.impl.AuthRepositoryImpl
import uz.gita.contacts.domain.repository.AuthRepository
import uz.gita.contacts.domain.repository.ContactRepository
import uz.gita.contacts.domain.repository.impl.ContactRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindContactRepository(impl: ContactRepositoryImpl): ContactRepository

}