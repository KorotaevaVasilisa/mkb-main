package ru.vsls.mkb.domain.repositories

interface SharedPreferencesRepository {

    fun getData(type: String, state: Boolean): Boolean

    fun putData(type: String, state: Boolean)
}
