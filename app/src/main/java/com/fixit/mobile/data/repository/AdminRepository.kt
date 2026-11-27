package com.fixit.mobile.data.repository

import com.fixit.mobile.data.remote.FixItNetwork
import com.fixit.mobile.data.remote.apiCall
import com.google.gson.JsonObject

class AdminRepository {
    private val api get() = FixItNetwork.api
    suspend fun dashboard(query: Map<String, String> = emptyMap()) = apiCall { api.adminDashboard(query) }
    suspend fun customers(query: Map<String, String> = emptyMap()) = apiCall { api.adminCustomers(query) }
    suspend fun customer(id: String) = apiCall { api.adminCustomer(id) }
    suspend fun updateCustomer(id: String, body: JsonObject) = apiCall { api.updateAdminCustomer(id, body) }
    suspend fun providers(query: Map<String, String> = emptyMap()) = apiCall { api.adminProviders(query) }
    suspend fun provider(id: String) = apiCall { api.adminProvider(id) }
    suspend fun updateProvider(id: String, body: JsonObject) = apiCall { api.updateAdminProvider(id, body) }
    suspend fun verifications(query: Map<String, String> = emptyMap()) = apiCall { api.adminVerifications(query) }
    suspend fun decideVerification(id: String, body: JsonObject) = apiCall { api.decideVerification(id, body) }
    suspend fun jobs(query: Map<String, String> = emptyMap()) = apiCall { api.adminJobs(query) }
    suspend fun job(id: String) = apiCall { api.adminJob(id) }
    suspend fun updateJob(id: String, body: JsonObject) = apiCall { api.updateAdminJob(id, body) }
    suspend fun disputes(query: Map<String, String> = emptyMap()) = apiCall { api.adminDisputes(query) }
    suspend fun dispute(id: String) = apiCall { api.adminDispute(id) }
    suspend fun updateDispute(id: String, body: JsonObject) = apiCall { api.updateAdminDispute(id, body) }
    suspend fun supportCases(query: Map<String, String> = emptyMap()) = apiCall { api.adminSupportCases(query) }
    suspend fun replyToSupportCase(id: String, body: JsonObject) = apiCall { api.replyToSupportCase(id, body) }
    suspend fun categories(query: Map<String, String> = emptyMap()) = apiCall { api.adminCategories(query) }
    suspend fun createCategory(body: JsonObject) = apiCall { api.createAdminCategory(body) }
    suspend fun updateCategory(id: String, body: JsonObject) = apiCall { api.updateAdminCategory(id, body) }
    suspend fun deleteCategory(id: String) = apiCall { api.deleteAdminCategory(id) }
    suspend fun analytics(query: Map<String, String> = emptyMap()) = apiCall { api.adminAnalytics(query) }
    suspend fun settings() = apiCall { api.adminSettings() }
    suspend fun updateSettings(body: JsonObject) = apiCall { api.updateAdminSettings(body) }
    suspend fun auditEvents(query: Map<String, String> = emptyMap()) = apiCall { api.adminAuditEvents(query) }
}
