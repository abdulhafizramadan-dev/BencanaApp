package com.ahr.gigihfinalproject.data.mapper

import com.ahr.gigihfinalproject.data.local.entity.DisasterGeometryEntity
import com.ahr.gigihfinalproject.data.local.entity.DisasterPropertiesEntity
import com.ahr.gigihfinalproject.data.network.response.DisasterGeometriesItem
import com.ahr.gigihfinalproject.data.network.response.DisasterPropertiesItem
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterProperties
import com.ahr.gigihfinalproject.util.emptyString

/**
 * Mapper from DisasterGeometriesItem to DisasterGeometryEntity
 */

@JvmName("disasterGeometryItemsToEntities")
fun List<DisasterGeometriesItem>.toEntities(): List<DisasterGeometryEntity> =
    map { it.toEntity() }

fun DisasterGeometriesItem.toEntity(): DisasterGeometryEntity =
    DisasterGeometryEntity(
        coordinates = coordinates ?: emptyList(),
        type = type ?: emptyString(),
        disasterProperties = disasterProperties?.toEntity() ?: DisasterPropertiesEntity()
    )

fun DisasterPropertiesItem.toEntity(): DisasterPropertiesEntity =
    DisasterPropertiesEntity(
        imageUrl = imageUrl ?: emptyString(),
        disasterType = disasterType ?: emptyString(),
        createdAt = createdAt ?: emptyString(),
        pkey = pkey ?: emptyString(),
        source = source ?: emptyString(),
        text = text ?: emptyString(),
        status = status ?: emptyString()
    )


/**
 * Mapper from DisasterGeometryEntity to DisasterGeometry
 */

@JvmName("disasterGeometryEntitiesToDomains")
fun List<DisasterGeometryEntity>.toDomains(): List<DisasterGeometry> =
    map { it.toDomain() }

fun DisasterGeometryEntity.toDomain(): DisasterGeometry =
    DisasterGeometry(
        coordinates = coordinates,
        type = type,
        disasterProperties = disasterProperties.toDomain()
    )

fun DisasterPropertiesEntity.toDomain(): DisasterProperties =
    DisasterProperties(
        imageUrl = imageUrl,
        disasterType = disasterType,
        createdAt = createdAt,
        pkey = pkey,
        source = source,
        text = text,
        status = status
    )


/**
 * Mapper from DisasterGeometryItems to DisasterGeometry
 */

@JvmName("disasterGeometriesItemToDomains")
fun List<DisasterGeometriesItem>.toDomains(): List<DisasterGeometry> =
    map { it.toDomain() }

fun DisasterGeometriesItem.toDomain(): DisasterGeometry =
    DisasterGeometry(
        coordinates = coordinates ?: emptyList(),
        type = type ?: emptyString(),
        disasterProperties = disasterProperties?.toDomain() ?: DisasterProperties()
    )

fun DisasterPropertiesItem.toDomain(): DisasterProperties =
    DisasterProperties(
        imageUrl = imageUrl ?: emptyString(),
        disasterType = disasterType ?: emptyString(),
        createdAt = createdAt ?: emptyString(),
        pkey = pkey ?: emptyString(),
        source = source ?: emptyString(),
        text = text ?: emptyString(),
        status = status ?: emptyString()
    )