package com.fixit.mobile.data.remote

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CancellationException
import retrofit2.Response
import java.io.IOException

suspend fun apiCall(block: suspend () -> Response<JsonObject>): ApiResult<JsonObject> = try {
    val response = block()
    if (response.isSuccessful) ApiResult.Success(response.body() ?: JsonObject())
    else {
        val raw = response.errorBody()?.string().orEmpty()
        val payload = runCatching { JsonParser.parseString(raw).asJsonObject }.getOrNull()
        val message = payload?.getAsJsonObject("error")?.get("message")?.asString
            ?: payload?.get("message")?.asString
            ?: when (response.code()) {
                400, 422 -> "Some information is invalid. Review it and try again."
                401 -> "Your session has expired. Sign in again."
                403 -> "You do not have permission to perform this action."
                404 -> "The requested item could not be found."
                409 -> "This item has changed. Refresh and try again."
                429 -> "Too many requests. Wait before trying again."
                in 500..599 -> "The server could not complete the request. Try again later."
                else -> "The request failed. Please try again."
            }
        ApiResult.Failure(ApiFailure(
            message,
            payload?.getAsJsonObject("error")?.get("code")?.asString ?: "HTTP_" + response.code(),
            response.code(),
            response.headers()["Retry-After"]
        ))
    }
} catch (cancelled: CancellationException) {
    throw cancelled
} catch (error: IOException) {
    ApiResult.Failure(ApiFailure("Could not reach FixIt. Check your connection and try again.", "NETWORK_ERROR"))
} catch (error: Exception) {
    ApiResult.Failure(ApiFailure(error.message ?: "Unexpected API error.", "API_ERROR"))
}
