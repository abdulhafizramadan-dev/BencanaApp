package com.ahr.gigihfinalproject.data.mapper

import com.ahr.gigihfinalproject.data.network.response.DisasterGeometriesItem
import com.ahr.gigihfinalproject.data.network.response.DisasterPropertiesItem
import com.ahr.gigihfinalproject.domain.model.DisasterGeometry
import com.ahr.gigihfinalproject.domain.model.DisasterProperties
import com.ahr.gigihfinalproject.util.emptyString

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