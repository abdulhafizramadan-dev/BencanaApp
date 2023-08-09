package com.ahr.gigihfinalproject.util

import com.ahr.gigihfinalproject.domain.model.TmaMonitoringGeometries
import com.ahr.gigihfinalproject.domain.model.TmaMonitoringObservationItem
import com.ahr.gigihfinalproject.domain.model.TmaMonitoringProperties

object DataDummy {
    fun getTmaMonitoringGeometryDummy(): TmaMonitoringGeometries {
        return TmaMonitoringGeometries(
            type = "Point",
            properties = TmaMonitoringProperties(
                gaugeid = "TMA00001",
                gaugenameid = "Bendung Katulampa",
                observations = listOf(
                    TmaMonitoringObservationItem(
                        f1 = "2016-12-09T04:00:00+00:00",
                        f2 = 30,
                        f3 = 4,
                        f4 = "SIAGA IV"
                    )
                )
            )
        )
    }
}