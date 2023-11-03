package ru.vsls.mkb.presentation.standard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel
import ru.vsls.mkb.domain.repositories.DatabaseRepository
import javax.inject.Inject

@HiltViewModel
class DiseasesViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val id: Int? = savedStateHandle["parentId"]
    private val _diseasesWithChildren =
        MutableStateFlow<List<ClassificationWithChildrenModel>>(emptyList())
    val diseasesWithChildren = _diseasesWithChildren.asStateFlow()

    init {
        if (id != null) getAllDiseasesFromDatabaseWithChildren(id) else getAllDiseasesFromDatabaseWithChildren()
    }

    private fun getAllDiseasesFromDatabaseWithChildren(id: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.getDiseasesByParentId(id)
            _diseasesWithChildren.update { data }
        }
    }

//    fun getFoundMatches(text:String){
//        viewModelScope.launch(Dispatchers.IO) {
//            val data = repository.getSearchDiseases(text)
//        }
//    }

}
