package com.ahr.gigihfinalproject.data.network.response

import com.google.gson.annotations.SerializedName

data class DisasterReportResponse(

	@field:SerializedName("result")
	val disasterResult: DisasterResult? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class DisasterResult(

	@field:SerializedName("objects")
	val disasterObjects: DisasterObjects? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class DisasterObjects(

	@field:SerializedName("output")
	val disasterOutput: DisasterOutput? = null
)

data class DisasterOutput(

	@field:SerializedName("geometries")
	val geometries: List<DisasterGeometriesItem>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class DisasterGeometriesItem(

	@field:SerializedName("coordinates")
	val coordinates: List<Double>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val disasterProperties: DisasterPropertiesItem? = null
)

data class DisasterPropertiesItem(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("disaster_type")
	val disasterType: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("pkey")
	val pkey: String? = null,

	@field:SerializedName("source")
	val source: String? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
