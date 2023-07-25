package com.ahr.gigihfinalproject.domain.model

import com.ahr.gigihfinalproject.util.emptyString

data class DisasterGeometry(

    val coordinates: List<Double> = emptyList(),

    val type: String = emptyString(),

    val disasterProperties: DisasterProperties = DisasterProperties()
)

data class DisasterProperties(

    val imageUrl: String = emptyString(),

    val disasterType: String = emptyString(),

    val createdAt: String = emptyString(),

    val pkey: String = emptyString(),

    val source: String = emptyString(),

    val text: String = emptyString(),

    val status: String = emptyString()
)