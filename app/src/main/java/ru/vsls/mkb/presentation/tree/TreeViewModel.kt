package ru.vsls.mkb.presentation.tree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ru.vsls.mkb.presentation.models.ClassificationTreeModel
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
class TreeViewModel @Inject constructor(
    private val repository: DatabaseRepository,
) : ViewModel() {

    private val _diseasesTreeState = MutableStateFlow(DiseasesTreeState())
    val diseasesTreeState = _diseasesTreeState.asStateFlow()

    init {
        getAllDiseasesFromDatabaseWithChildren()
    }

    private fun getAllDiseasesFromDatabaseWithChildren(id: Int = 0) {
        viewModelScope.launch(Dispatchers.IO) {
            val diseases = repository.getDiseasesByParentId(id)
            val tree = diseases.map { it.mapToUiModel() }
            _diseasesTreeState.update {
                it.copy(tree = tree)
            }
        }
    }

    fun searchDiseases(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = repository.searchDiseases(name)
            val tree = data.map { it.mapToUiModel() }
            _diseasesTreeState.update {
                it.copy(tree = tree)
            }
        }
    }

    fun loadChildren(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            // получаем список из БД
            val diseases = repository.getDiseasesByParentId(id)
            // переводим ClassificationWithChildrenModel в ClassificationTreeModel
            val newBranch = diseases.map { it.mapToUiModel() }
            // берем существующее дерево
            val existTree = _diseasesTreeState.value.tree
            // прекрепляем дочерние узлы к существующему дереву
            val newTree = setBranchToTree(id, existTree, newBranch)

            _diseasesTreeState.update {
                it.copy(tree = newTree)
            }
        }
    }

    /**
     * Прекрепляем "ветку к дереву" (дочерние классификации к родительской классификации)
     * функция работает рекурсивно, т.е. будет пытаться прикрепить ветку в дочерних узлах
     *
     * @param needleNodeId id узла к которому будет прекреалены дочерние классификации
     * @param existTree дерево (классификации) к которому необходимо прикрепить дочерние классификации
     * @param newBranch дочерние классификации, которые нужно прикрепить к родительским
     */
    private fun setBranchToTree(
        needleNodeId: Int,
        existTree: List<ClassificationTreeModel>,
        newBranch: List<ClassificationTreeModel>,
    ): List<ClassificationTreeModel> {
        val newTree = mutableListOf<ClassificationTreeModel>()
        for (node in existTree) {
            if (node.id == needleNodeId) {
                val newNodeWithChildren = node.copy(children = newBranch)
                newTree += newNodeWithChildren
            } else if (node.children.isNotEmpty()) {
                val children = setBranchToTree(needleNodeId, node.children, newBranch)
                val newNodeWithChildren = node.copy(children = children)
                newTree += newNodeWithChildren
            } else {
                newTree += node
            }
        }
        return newTree
    }

    private fun ClassificationWithChildrenModel.mapToUiModel(): ClassificationTreeModel =
        ClassificationTreeModel(
            id = disease.id,
            parentId = disease.parentId,
            mkbCode = disease.mkbCode,
            mkbName = disease.mkbName,
            hasChildren = children.isNotEmpty(),
            // изначально устанавливаем пустой список, при клике на узел если hasChildren = true,
            // в children будут загружены дочерние узлы
            children = emptyList()
        )

    fun changeContent(value: String) {
        _diseasesTreeState.update {
            it.copy(searchContent = value)
        }
    }
}
