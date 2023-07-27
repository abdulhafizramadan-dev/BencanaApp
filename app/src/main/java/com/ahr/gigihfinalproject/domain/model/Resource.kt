package com.ahr.gigihfinalproject.domain.model

sealed interface Resource<out T> {
    object Idling : Resource<Nothing>
    object Loading : Resource<Nothing>
    data class Success<out T>(val data: T) : Resource<T>
    data class Error<out T>(val error: Throwable, val data: T? = null) : Resource<T>
}
