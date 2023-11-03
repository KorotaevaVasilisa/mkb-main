package ru.vsls.mkb.data.dto

import androidx.room.Embedded
import androidx.room.Relation
import ru.vsls.mkb.data.DiseasesEntity

data class DiseasesWithChildren(
    @Embedded val disease: DiseasesEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parent_id",
        entity = DiseasesEntity::class
    )
    val children: List<DiseasesEntity>,
)
