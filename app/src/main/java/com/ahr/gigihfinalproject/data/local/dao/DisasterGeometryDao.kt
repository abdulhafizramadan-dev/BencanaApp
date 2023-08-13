package com.ahr.gigihfinalproject.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.ahr.gigihfinalproject.data.local.entity.DisasterGeometryEntity

@Dao
interface DisasterGeometryDao {

    @Query("SELECT * FROM DisasterGeometryEntity")
    suspend fun getDisasterGeometriesEntities(): List<DisasterGeometryEntity>

    @Upsert
    suspend fun upsertDisasterGeometryEntities(disasterGeometryEntities: List<DisasterGeometryEntity>)

    @Query("DELETE FROM DisasterGeometryEntity")
    suspend fun clearDisasterGeometriesEntities()

}