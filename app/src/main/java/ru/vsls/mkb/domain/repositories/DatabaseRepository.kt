package ru.vsls.mkb.domain.repositories

import ru.vsls.mkb.domain.model.ClassificationModel
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel
import kotlinx.coroutines.flow.Flow
import ru.vsls.mkb.data.DiseasesEntity

interface DatabaseRepository {

    fun insertData(diseases: List<ClassificationModel>)

    suspend fun getDiseasesByParentId(parentId: Int): List<ClassificationWithChildrenModel>

//    suspend fun getSearchDiseases(search:String):List<DiseasesEntity>
}
