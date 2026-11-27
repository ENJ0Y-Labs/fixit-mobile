package com.fixit.mobile.data.remote

import com.google.gson.JsonElement
import com.google.gson.JsonObject

data class ApiErrorBody(val code: String? = null, val message: String? = null, val details: JsonElement? = null)
data class ApiEnvelope<T>(val data: T? = null, val error: ApiErrorBody? = null)
data class ApiFailure(val message: String, val code: String, val httpStatus: Int? = null, val retryAfter: String? = null)
sealed interface ApiResult<out T> {
    data class Success<T>(val value: T) : ApiResult<T>
    data class Failure(val error: ApiFailure) : ApiResult<Nothing>
}
