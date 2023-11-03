package ru.vsls.mkb.data.repositories.impl

import ru.vsls.mkb.data.DiseasesDao
import ru.vsls.mkb.data.mapper.mapToDomain
import ru.vsls.mkb.data.mapper.mapToEntity
import ru.vsls.mkb.domain.model.ClassificationModel
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel
import ru.vsls.mkb.domain.repositories.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vsls.mkb.data.DiseasesEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(private val diseasesDao: DiseasesDao) :
    DatabaseRepository {
//    override fun getAllDiseases(id: Int): Flow<List<ClassificationModel>> {
//        return diseasesDao.getAll(id = id).map { list ->
//            list.map { it.mapToDomain() }
//        }
//    }

    override fun insertData(diseases: List<ClassificationModel>) {
        diseasesDao.insert(diseases.map { it.mapToEntity() })
    }

    override suspend fun getDiseasesByParentId(parentId: Int): List<ClassificationWithChildrenModel> {
        return diseasesDao.getDiseasesWithChildren(parentId).map { it.mapToDomain() }
    }

//    override suspend fun getSearchDiseases(search: String): List<DiseasesEntity> {
//        return diseasesDao.filtered(search)
//    }
}
