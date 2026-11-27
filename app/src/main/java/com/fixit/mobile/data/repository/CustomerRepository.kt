package com.fixit.mobile.data.repository

import com.fixit.mobile.data.remote.FixItNetwork
import com.fixit.mobile.data.remote.apiCall
import com.google.gson.JsonObject

class CustomerRepository {
    private val api get() = FixItNetwork.api
    suspend fun profile() = apiCall { api.customerProfile() }
    suspend fun updateProfile(body: JsonObject) = apiCall { api.updateCustomerProfile(body) }
    suspend fun categories(query: Map<String, String> = emptyMap()) = apiCall { api.publicCategories(query) }
    suspend fun services(query: Map<String, String> = emptyMap()) = apiCall { api.publicServices(query) }
    suspend fun providers(query: Map<String, String> = emptyMap()) = apiCall { api.publicProviders(query) }
    suspend fun provider(id: String) = apiCall { api.publicProvider(id) }
    suspend fun providerReviews(id: String, query: Map<String, String> = emptyMap()) = apiCall { api.publicProviderReviews(id, query) }
    suspend fun jobs(limit: Int? = 50, cursor: String? = null) = apiCall { api.customerJobs(limit, cursor) }
    suspend fun job(id: String) = apiCall { api.customerJob(id) }
    suspend fun createJob(body: JsonObject) = apiCall { api.createCustomerJob(body) }
    suspend fun updateJob(id: String, body: JsonObject) = apiCall { api.updateCustomerJob(id, body) }
    suspend fun cancelJob(id: String) = apiCall { api.cancelCustomerJob(id) }
    suspend fun bids(jobId: String) = apiCall { api.customerJobBids(jobId) }
    suspend fun acceptBid(jobId: String, bidId: String) = apiCall { api.acceptBid(jobId, bidId) }
    suspend fun notifications(query: Map<String, String> = emptyMap()) = apiCall { api.customerNotifications(query) }
    suspend fun markNotificationRead(id: String) = apiCall { api.updateCustomerNotification(id, JsonObject().apply { addProperty("read", true) }) }
    suspend fun reviewJob(id: String, body: JsonObject) = apiCall { api.reviewJob(id, body) }
    suspend fun payments(query: Map<String, String> = emptyMap()) = apiCall { api.customerPayments(query) }
    suspend fun createPaymentIntent(id: String, body: JsonObject) = apiCall { api.createPaymentIntent(id, body) }
    suspend fun payment(id: String) = apiCall { api.customerPayment(id) }
    suspend fun favorites() = apiCall { api.customerFavorites() }
    suspend fun addFavorite(id: String) = apiCall { api.addFavorite(id) }
    suspend fun removeFavorite(id: String) = apiCall { api.removeFavorite(id) }
}
