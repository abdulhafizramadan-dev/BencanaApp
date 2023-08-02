package com.ahr.gigihfinalproject.domain.model

import com.ahr.gigihfinalproject.util.emptyString

data class DisasterFilterTimePeriod(
    val timeSecond: Long = 0,
    val name: String = emptyString(),
    val selected: Boolean = false
)
