package ru.vsls.mkb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4(contentEntity = DiseasesEntity::class)
@Entity(tableName = "diseasesFts")
data class DiseasesFtsEntity(
    @PrimaryKey @ColumnInfo(name = "rowid") val id: Int,
    @ColumnInfo(name = "mkb_name") val mkbName: String,
)
