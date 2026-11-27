package com.fixit.mobile.data.remote

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

/** Routes mirror the proposed FixIt v1 catalogue in fixit-frontend/README.md. */
interface FixItApi {
    // Authentication
    @POST("auth/register") suspend fun register(@Body body: JsonObject): Response<JsonObject>
    @POST("auth/login") suspend fun login(@Body body: JsonObject): Response<JsonObject>
    @POST("auth/logout") suspend fun logout(): Response<JsonObject>
    @GET("auth/me") suspend fun currentUser(): Response<JsonObject>
    @POST("auth/password/forgot") suspend fun forgotPassword(@Body body: JsonObject): Response<JsonObject>
    @POST("auth/password/reset") suspend fun resetPassword(@Body body: JsonObject): Response<JsonObject>
    @POST("auth/verification/request") suspend fun requestVerification(@Body body: JsonObject): Response<JsonObject>
    @POST("auth/verification/confirm") suspend fun confirmVerification(@Body body: JsonObject): Response<JsonObject>

    // Public marketplace
    @GET("public/categories") suspend fun publicCategories(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("public/services") suspend fun publicServices(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("public/providers") suspend fun publicProviders(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("public/providers/{id}") suspend fun publicProvider(@Path("id") id: String): Response<JsonObject>
    @GET("public/providers/{id}/reviews") suspend fun publicProviderReviews(@Path("id") id: String, @QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>

    // Customer
    @GET("customer/me") suspend fun customerProfile(): Response<JsonObject>
    @PATCH("customer/me") suspend fun updateCustomerProfile(@Body body: JsonObject): Response<JsonObject>
    @GET("customer/me/jobs") suspend fun customerJobs(@Query("limit") limit: Int? = null, @Query("cursor") cursor: String? = null): Response<JsonObject>
    @GET("customer/me/jobs/{id}") suspend fun customerJob(@Path("id") id: String): Response<JsonObject>
    @POST("customer/me/jobs") suspend fun createCustomerJob(@Body body: JsonObject): Response<JsonObject>
    @PATCH("customer/me/jobs/{id}") suspend fun updateCustomerJob(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @POST("customer/me/jobs/{id}/cancel") suspend fun cancelCustomerJob(@Path("id") id: String, @Body body: JsonObject = JsonObject()): Response<JsonObject>
    @GET("customer/me/jobs/{id}/bids") suspend fun customerJobBids(@Path("id") id: String): Response<JsonObject>
    @POST("customer/me/jobs/{jobId}/bids/{bidId}/accept") suspend fun acceptBid(@Path("jobId") jobId: String, @Path("bidId") bidId: String): Response<JsonObject>
    @GET("customer/me/notifications") suspend fun customerNotifications(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @PATCH("customer/me/notifications/{id}") suspend fun updateCustomerNotification(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @POST("customer/me/jobs/{id}/review") suspend fun reviewJob(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("customer/me/payments") suspend fun customerPayments(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @POST("customer/me/jobs/{id}/payment-intents") suspend fun createPaymentIntent(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("customer/me/payments/{id}") suspend fun customerPayment(@Path("id") id: String): Response<JsonObject>
    @GET("customer/me/favorites") suspend fun customerFavorites(): Response<JsonObject>
    @PUT("customer/me/favorites/{id}") suspend fun addFavorite(@Path("id") id: String): Response<JsonObject>
    @DELETE("customer/me/favorites/{id}") suspend fun removeFavorite(@Path("id") id: String): Response<JsonObject>

    // Provider
    @GET("provider/me") suspend fun providerProfile(): Response<JsonObject>
    @PATCH("provider/me") suspend fun updateProviderProfile(@Body body: JsonObject): Response<JsonObject>
    @GET("provider/me/services") suspend fun providerServices(): Response<JsonObject>
    @POST("provider/me/services") suspend fun addProviderService(@Body body: JsonObject): Response<JsonObject>
    @PATCH("provider/me/services/{id}") suspend fun updateProviderService(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @DELETE("provider/me/services/{id}") suspend fun removeProviderService(@Path("id") id: String): Response<JsonObject>
    @GET("provider/me/availability") suspend fun providerAvailability(): Response<JsonObject>
    @PUT("provider/me/availability") suspend fun updateProviderAvailability(@Body body: JsonObject): Response<JsonObject>
    @GET("provider/jobs") suspend fun providerOpportunities(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("provider/jobs/{id}") suspend fun providerJob(@Path("id") id: String): Response<JsonObject>
    @GET("provider/me/bids") suspend fun providerBids(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @POST("provider/jobs/{id}/bids") suspend fun submitBid(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("provider/me/jobs") suspend fun providerJobs(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @PATCH("provider/me/jobs/{id}/progress") suspend fun updateProviderJobProgress(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("provider/me/earnings") suspend fun providerEarnings(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("provider/me/reviews") suspend fun providerReviews(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("provider/me/settings") suspend fun providerSettings(): Response<JsonObject>
    @PATCH("provider/me/settings") suspend fun updateProviderSettings(@Body body: JsonObject): Response<JsonObject>
    @GET("provider/me/dashboard") suspend fun providerDashboard(): Response<JsonObject>
    @GET("provider/me/notifications") suspend fun providerNotifications(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>

    // Admin
    @GET("admin/dashboard") suspend fun adminDashboard(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/customers") suspend fun adminCustomers(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/customers/{id}") suspend fun adminCustomer(@Path("id") id: String): Response<JsonObject>
    @PATCH("admin/customers/{id}") suspend fun updateAdminCustomer(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/providers") suspend fun adminProviders(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/providers/{id}") suspend fun adminProvider(@Path("id") id: String): Response<JsonObject>
    @PATCH("admin/providers/{id}") suspend fun updateAdminProvider(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/verifications") suspend fun adminVerifications(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @POST("admin/verifications/{id}/decision") suspend fun decideVerification(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/jobs") suspend fun adminJobs(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/jobs/{id}") suspend fun adminJob(@Path("id") id: String): Response<JsonObject>
    @PATCH("admin/jobs/{id}") suspend fun updateAdminJob(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/disputes") suspend fun adminDisputes(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/disputes/{id}") suspend fun adminDispute(@Path("id") id: String): Response<JsonObject>
    @PATCH("admin/disputes/{id}") suspend fun updateAdminDispute(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/support-cases") suspend fun adminSupportCases(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @POST("admin/support-cases/{id}/messages") suspend fun replyToSupportCase(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @GET("admin/categories") suspend fun adminCategories(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @POST("admin/categories") suspend fun createAdminCategory(@Body body: JsonObject): Response<JsonObject>
    @PATCH("admin/categories/{id}") suspend fun updateAdminCategory(@Path("id") id: String, @Body body: JsonObject): Response<JsonObject>
    @DELETE("admin/categories/{id}") suspend fun deleteAdminCategory(@Path("id") id: String): Response<JsonObject>
    @GET("admin/analytics") suspend fun adminAnalytics(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>
    @GET("admin/settings") suspend fun adminSettings(): Response<JsonObject>
    @PATCH("admin/settings") suspend fun updateAdminSettings(@Body body: JsonObject): Response<JsonObject>
    @GET("admin/audit-events") suspend fun adminAuditEvents(@QueryMap query: Map<String, String> = emptyMap()): Response<JsonObject>

    // Upload field names and constraints remain subject to backend confirmation.
    @Multipart @POST("uploads")
    suspend fun upload(@Part file: MultipartBody.Part, @PartMap fields: Map<String, @JvmSuppressWildcards RequestBody> = emptyMap()): Response<JsonObject>
    @GET("uploads/{id}") suspend fun uploadMetadata(@Path("id") id: String): Response<JsonObject>
}
