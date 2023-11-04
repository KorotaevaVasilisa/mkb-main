package ru.vsls.mkb.presentation.standard

import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel

data class DiseasesStandardState(
    val diseases: List<ClassificationWithChildrenModel> = emptyList(),
    var searchContent: String = "",
)
