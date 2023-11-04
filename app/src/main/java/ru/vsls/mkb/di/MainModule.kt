package ru.vsls.mkb.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.vsls.mkb.domain.useCase.LoaderDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.vsls.mkb.data.AppDatabase
import ru.vsls.mkb.data.DiseasesDao
import ru.vsls.mkb.data.repositories.impl.DatabaseRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideAppDatabase(app: Application, useCase: LoaderDataUseCase): AppDatabase {
        val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                val list = useCase.execute()
                var query = "INSERT INTO diseases (id,parent_id,mkb_code,mkb_name) VALUES "
                for ((index, model) in list.withIndex()) {
                    query += " (${model.id}, ${model.parentId}, ${model.mkbCode}, ${model.mkbName})"
                    if (index != list.lastIndex) {
                        query += ","
                    }
                }
                db.execSQL(query)
            }
        }

        return Room.databaseBuilder(app, AppDatabase::class.java, "diseases.db")
//            .addCallback(roomCallback)
            .createFromAsset("diseases.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideDiseaseDao(appDatabase: AppDatabase): DiseasesDao {
        return appDatabase.getDiseasesDao()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepository(diseasesDao: DiseasesDao) = DatabaseRepositoryImpl(diseasesDao)

}
