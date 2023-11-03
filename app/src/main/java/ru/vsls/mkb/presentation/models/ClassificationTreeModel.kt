package ru.vsls.mkb.presentation.models

data class ClassificationTreeModel(
    val id: Int,
    val parentId: Int,
    val mkbCode: String,
    val mkbName: String,
    val hasChildren: Boolean,
    val children: List<ClassificationTreeModel>
)
