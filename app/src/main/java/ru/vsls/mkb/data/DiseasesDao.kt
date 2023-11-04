package ru.vsls.mkb.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import ru.vsls.mkb.data.dto.DiseasesWithChildren
import kotlinx.coroutines.flow.Flow

@Dao
interface DiseasesDao {
    @Query("SELECT * FROM diseases WHERE parent_id =:id")
    fun getAll(id: Int): Flow<List<DiseasesEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(diseases: List<DiseasesEntity>)

    @Transaction
    @Query("SELECT * FROM diseases WHERE parent_id =:id")
    suspend fun getDiseasesWithChildren(id: Int): List<DiseasesWithChildren>

    @Transaction
    @Query("SELECT * FROM diseases WHERE mkb_name LIKE '%' || :name || '%'")
    suspend fun searchDiseases(name: String): List<DiseasesWithChildren>
}
