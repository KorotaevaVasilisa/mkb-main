package ru.vsls.mkb.presentation.standard

import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel

/**
 * Состояние экрана
 * @property confirmedSearchString строки поиска которую запросил пользователь (нажав на кнопку поиска)
 */
data class DiseasesStandardState(
    val diseases: List<ClassificationWithChildrenModel> = emptyList(),
    val searchContent: String = "",
    val confirmedSearchString: String = ""
)
