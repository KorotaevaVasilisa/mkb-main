package ru.vsls.mkb.presentation.tree

import ru.vsls.mkb.presentation.models.ClassificationTreeModel

data class DiseasesTreeState(
    val tree: List<ClassificationTreeModel> = emptyList(),
    val searchContent: String = "",
)
