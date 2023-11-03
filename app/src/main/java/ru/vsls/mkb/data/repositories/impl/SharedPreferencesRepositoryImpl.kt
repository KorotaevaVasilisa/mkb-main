package ru.vsls.mkb.data.repositories.impl

import android.content.Context
import androidx.preference.PreferenceManager
import ru.vsls.mkb.domain.repositories.SharedPreferencesRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferencesRepositoryImpl @Inject constructor(@ApplicationContext private val context: Context) :
    SharedPreferencesRepository {

    private val mSettings = PreferenceManager.getDefaultSharedPreferences(context)

    override fun getData(type: String, state: Boolean): Boolean {
        return mSettings.getBoolean(type, state)
    }

    override fun putData(type: String, state: Boolean) {
        mSettings.edit().putBoolean(type, state).apply()
    }

}
