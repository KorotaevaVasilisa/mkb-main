package ru.vsls.mkb.data.mapper

import ru.vsls.mkb.data.DiseasesEntity
import ru.vsls.mkb.data.dto.DiseasesWithChildren
import ru.vsls.mkb.domain.model.ClassificationModel
import ru.vsls.mkb.domain.model.ClassificationWithChildrenModel

fun DiseasesEntity.mapToDomain(): ClassificationModel = ClassificationModel(
    id = id,
    parentId = parentId,
    mkbCode = mkbCode,
    mkbName = mkbName
)

fun ClassificationModel.mapToEntity(): DiseasesEntity = DiseasesEntity(
    id = id,
    parentId = parentId,
    mkbCode = mkbCode,
    mkbName = mkbName
)

fun DiseasesWithChildren.mapToDomain(): ClassificationWithChildrenModel =
    ClassificationWithChildrenModel(
        disease = disease.mapToDomain(),
        children = children.map { it.mapToDomain() }
    )
