package com.ahr.gigihfinalproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ahr.gigihfinalproject.data.local.dao.DisasterGeometryDao
import com.ahr.gigihfinalproject.data.local.entity.DisasterGeometryEntity

@Database(
    entities = [DisasterGeometryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GigihFinalProjectDatabase : RoomDatabase() {

    abstract fun disasterGeometryDao(): DisasterGeometryDao

    companion object {
        const val DATABASE_NAME = "gigih_final_project.db"
    }

}