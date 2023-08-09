package com.ahr.gigihfinalproject.domain.model

import com.ahr.gigihfinalproject.util.emptyString
import com.google.gson.annotations.SerializedName

data class TmaMonitoringGeometries(

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("properties")
	val properties: TmaMonitoringProperties = TmaMonitoringProperties(),
)

data class TmaMonitoringProperties(

	val gaugenameid: String = emptyString(),

	val observations: List<TmaMonitoringObservationItem> = emptyList(),

	val gaugeid: String = emptyString(),
)

data class TmaMonitoringObservationItem(

	val f1: String = emptyString(),

	val f2: Int = 0,

	val f3: Int = 0,

	val f4: String = emptyString(),
)
