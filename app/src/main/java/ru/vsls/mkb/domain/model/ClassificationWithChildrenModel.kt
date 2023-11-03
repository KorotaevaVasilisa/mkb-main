package ru.vsls.mkb.domain.model

data class ClassificationWithChildrenModel(
    val disease: ClassificationModel,
    val children: List<ClassificationModel>
)
