package ru.vsls.mkb.presentation.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import ru.vsls.mkb.domain.repositories.SharedPreferencesRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SharedPreferencesRepository,
) : ViewModel() {
    private val APP_PREFERENCES_TYPE = "Type"

    private val _screenState = MutableStateFlow(MainScreenState())
    val screenState: StateFlow<MainScreenState> = _screenState.asStateFlow()

    init {
        _screenState.update {
            it.copy(switcher = getDataFromSharedPreferences())
        }
    }

    fun changeSearcherState() {
        _screenState.update {
            it.copy(searcher = !it.searcher)
        }
    }

    fun putDataInSharedPreferences() {
        _screenState.update {
            it.copy(switcher = !it.switcher)
        }
        repository.putData(APP_PREFERENCES_TYPE, screenState.value.switcher)
    }

    private fun getDataFromSharedPreferences(): Boolean {
        return repository.getData(APP_PREFERENCES_TYPE, screenState.value.switcher)
    }
}
