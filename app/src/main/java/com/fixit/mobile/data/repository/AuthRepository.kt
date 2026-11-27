package com.fixit.mobile.data.repository

import com.fixit.mobile.data.remote.ApiResult
import com.fixit.mobile.data.remote.FixItNetwork
import com.fixit.mobile.data.remote.apiCall
import com.google.gson.JsonObject

class AuthRepository {
    private val api get() = FixItNetwork.api
    suspend fun register(name: String, email: String, phone: String, password: String, role: String) =
        apiCall { api.register(JsonObject().apply {
            addProperty("name", name); addProperty("email", email); addProperty("phone", phone)
            addProperty("password", password); addProperty("role", role)
        }) }
    suspend fun login(email: String, password: String) = apiCall { api.login(JsonObject().apply {
        addProperty("email", email); addProperty("password", password)
    }) }
    suspend fun currentUser() = apiCall { api.currentUser() }
    suspend fun logout(): ApiResult<JsonObject> {
        val result = apiCall { api.logout() }
        if (result is ApiResult.Success) FixItNetwork.sessionCookieJar.clear()
        return result
    }
    suspend fun forgotPassword(email: String) = apiCall { api.forgotPassword(JsonObject().apply { addProperty("email", email) }) }
    suspend fun resetPassword(token: String, password: String) = apiCall { api.resetPassword(JsonObject().apply {
        addProperty("token", token); addProperty("password", password)
    }) }
}
