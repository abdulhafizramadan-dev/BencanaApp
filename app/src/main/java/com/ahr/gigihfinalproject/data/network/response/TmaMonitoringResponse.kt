package com.ahr.gigihfinalproject.data.network.response

import com.google.gson.annotations.SerializedName

data class TmaMonitoringResponse(

	@field:SerializedName("result")
	val result: TmaMonitoringResult? = null,

	@field:SerializedName("statusCode")
	val statusCode: Int? = null
)

data class TmaMonitoringResult(

	@field:SerializedName("objects")
	val objects: TmaMonitoringObjectItem? = null,

	)

data class TmaMonitoringGeometriesItem(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: TmaMonitoringPropertiesItemResponse? = null
)

data class TmaMonitoringObjectItem(

	@field:SerializedName("output")
	val output: TmaMonitoringOutput? = null
)

data class TmaMonitoringOutput(

	@field:SerializedName("geometries")
	val geometries: List<TmaMonitoringGeometriesItem>? = null,

	@field:SerializedName("type")
	val type: String? = null
)

data class TmaMonitoringPropertiesItemResponse(

	@field:SerializedName("gaugenameid")
	val gaugenameid: String? = null,

	@field:SerializedName("observations")
	val observations: List<TmaMonitoringObservationItemResponse>? = null,

	@field:SerializedName("gaugeid")
	val gaugeid: String? = null
)

data class TmaMonitoringObservationItemResponse(

	@field:SerializedName("f1")
	val f1: String? = null,

	@field:SerializedName("f2")
	val f2: Int? = null,

	@field:SerializedName("f3")
	val f3: Int? = null,

	@field:SerializedName("f4")
	val f4: String? = null
)
