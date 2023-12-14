package ru.vsls.mkb.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DiseasesEntity::class
    ], version = 2
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getDiseasesDao(): DiseasesDao
}
