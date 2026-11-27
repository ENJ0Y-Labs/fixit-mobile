package com.fixit.mobile.data.repository

import com.fixit.mobile.data.remote.FixItNetwork
import com.fixit.mobile.data.remote.apiCall
import com.google.gson.JsonObject

class ProviderRepository {
    private val api get() = FixItNetwork.api
    suspend fun profile() = apiCall { api.providerProfile() }
    suspend fun updateProfile(body: JsonObject) = apiCall { api.updateProviderProfile(body) }
    suspend fun services() = apiCall { api.providerServices() }
    suspend fun addService(body: JsonObject) = apiCall { api.addProviderService(body) }
    suspend fun updateService(id: String, body: JsonObject) = apiCall { api.updateProviderService(id, body) }
    suspend fun removeService(id: String) = apiCall { api.removeProviderService(id) }
    suspend fun availability() = apiCall { api.providerAvailability() }
    suspend fun updateAvailability(body: JsonObject) = apiCall { api.updateProviderAvailability(body) }
    suspend fun opportunities(query: Map<String, String> = emptyMap()) = apiCall { api.providerOpportunities(query) }
    suspend fun job(id: String) = apiCall { api.providerJob(id) }
    suspend fun bids(query: Map<String, String> = emptyMap()) = apiCall { api.providerBids(query) }
    suspend fun submitBid(id: String, body: JsonObject) = apiCall { api.submitBid(id, body) }
    suspend fun jobs(query: Map<String, String> = emptyMap()) = apiCall { api.providerJobs(query) }
    suspend fun updateJobProgress(id: String, body: JsonObject) = apiCall { api.updateProviderJobProgress(id, body) }
    suspend fun earnings(query: Map<String, String> = emptyMap()) = apiCall { api.providerEarnings(query) }
    suspend fun reviews(query: Map<String, String> = emptyMap()) = apiCall { api.providerReviews(query) }
    suspend fun settings() = apiCall { api.providerSettings() }
    suspend fun updateSettings(body: JsonObject) = apiCall { api.updateProviderSettings(body) }
    suspend fun dashboard() = apiCall { api.providerDashboard() }
    suspend fun notifications(query: Map<String, String> = emptyMap()) = apiCall { api.providerNotifications(query) }
}
