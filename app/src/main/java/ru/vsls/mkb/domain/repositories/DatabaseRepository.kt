package ru.vsls.mkb.domain.repositories

import ru.vsls.mkb.domain.model.ClassificationModel
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel

interface DatabaseRepository {

    fun insertData(diseases: List<ClassificationModel>)

    suspend fun getDiseasesByParentId(parentId: Int): List<ClassificationWithChildrenModel>

    suspend fun searchDiseases(name:String):List<ClassificationWithChildrenModel>
}
