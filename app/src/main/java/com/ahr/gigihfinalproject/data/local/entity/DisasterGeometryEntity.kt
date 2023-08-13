package com.ahr.gigihfinalproject.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.ahr.gigihfinalproject.util.emptyString

@Entity
@TypeConverters(DisasterGeometryCoordinatesConverter::class)
data class DisasterGeometryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val coordinates: List<Double> = emptyList(),

    val type: String = emptyString(),

    @Embedded
    val disasterProperties: DisasterPropertiesEntity = DisasterPropertiesEntity()
)

data class DisasterPropertiesEntity(

    val imageUrl: String = emptyString(),

    val disasterType: String = emptyString(),

    val createdAt: String = emptyString(),

    @PrimaryKey
    val pkey: String = emptyString(),

    val source: String = emptyString(),

    val text: String = emptyString(),

    val status: String = emptyString()
)

class DisasterGeometryCoordinatesConverter {

    @TypeConverter
    fun from(coordinates: List<Double>): String {
        return coordinates.toString()
    }

    @TypeConverter
    fun to(coordinatesString: String): List<Double> {
        return coordinatesString
            .replace("[", "")  // Remove opening square bracket
            .replace("]", "")  // Remove closing square bracket
            .split(",")        // Split the string into individual elements
            .map { it.trim().toDouble() }  // Convert each element to an integer
    }

}
