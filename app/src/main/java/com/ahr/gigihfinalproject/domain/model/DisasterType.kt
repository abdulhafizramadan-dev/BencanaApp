package com.ahr.gigihfinalproject.domain.model

import androidx.annotation.StringRes
import com.ahr.gigihfinalproject.R

enum class DisasterType(@StringRes val displayName: Int, val query: String) {
    Flood(R.string.flood, "flood"),
    Earthquake(R.string.earthquake, "earthquake"),
    Fire(R.string.fire, "fire"),
    Wind(R.string.wind, "wind"),
    Volcano(R.string.volcano, "volcano"),
}