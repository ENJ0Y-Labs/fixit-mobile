package com.fixit.mobile.ui.customer

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class CustomerDemoDataTest {
    @Test
    fun providerIdsAreUniqueAndEveryProviderHasAValidRating() {
        val providers = CustomerDemoData.providers
        assertEquals(providers.size, providers.map { it.id }.toSet().size)
        assertTrue(providers.all { it.rating in 0.0..5.0 })
        assertTrue(providers.all { it.hourlyRate > 0 })
    }

    @Test
    fun demoJobsUseKnownCustomerStatusesAndUniqueIds() {
        val jobs = CustomerDemoData.jobs
        val supportedStatuses = setOf("AWAITING_BIDS", "IN_PROGRESS", "COMPLETED", "CANCELLED")
        assertEquals(jobs.size, jobs.map { it.id }.toSet().size)
        assertTrue(jobs.all { it.status in supportedStatuses })
        assertTrue(jobs.any { it.status == "AWAITING_BIDS" })
        assertTrue(jobs.any { it.status == "IN_PROGRESS" })
        assertTrue(jobs.any { it.status == "COMPLETED" })
        assertTrue(jobs.any { it.status == "CANCELLED" })
    }

    @Test
    fun demoDataIsExplicitlyNigeriaRelevant() {
        assertTrue(CustomerDemoData.jobs.all { it.location.contains("Port Harcourt") })
        assertFalse(CustomerDemoData.categories.isEmpty())
    }
}
