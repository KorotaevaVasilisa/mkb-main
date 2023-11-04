package ru.vsls.mkb.data.repositories.impl

import ru.vsls.mkb.data.DiseasesDao
import ru.vsls.mkb.data.mapper.mapToDomain
import ru.vsls.mkb.data.mapper.mapToEntity
import ru.vsls.mkb.domain.model.ClassificationModel
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel
import ru.vsls.mkb.domain.repositories.DatabaseRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(private val diseasesDao: DiseasesDao) :
    DatabaseRepository {

    override fun insertData(diseases: List<ClassificationModel>) {
        diseasesDao.insert(diseases.map { it.mapToEntity() })
    }

    override suspend fun getDiseasesByParentId(parentId: Int): List<ClassificationWithChildrenModel> {
        return diseasesDao.getDiseasesWithChildren(parentId).map { it.mapToDomain() }
    }

    override suspend fun searchDiseases(name: String): List<ClassificationWithChildrenModel> {
        return diseasesDao.searchDiseases(name).map { it.mapToDomain() }
    }

}
