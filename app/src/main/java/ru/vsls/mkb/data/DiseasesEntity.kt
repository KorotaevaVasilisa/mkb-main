package ru.vsls.mkb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "diseases")
data class DiseasesEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "parent_id", index = true)  val parentId: Int,
    @ColumnInfo(name = "mkb_code") val mkbCode: String,
    @ColumnInfo(name = "mkb_name") val mkbName: String,
)
