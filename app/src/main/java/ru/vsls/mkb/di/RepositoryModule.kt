package ru.vsls.mkb.di

import ru.vsls.mkb.domain.repositories.DatabaseRepository
import ru.vsls.mkb.domain.repositories.SharedPreferencesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vsls.mkb.data.repositories.impl.DatabaseRepositoryImpl
import ru.vsls.mkb.data.repositories.impl.SharedPreferencesRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindPatientRepository(impl: DatabaseRepositoryImpl): DatabaseRepository


    @Binds
    @Singleton
    fun bindSharedPreferencesRepository(impl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
}
