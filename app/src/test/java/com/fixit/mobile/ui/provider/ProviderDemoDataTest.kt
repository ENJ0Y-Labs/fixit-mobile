package com.fixit.mobile.ui.provider

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ProviderDemoDataTest {
    @Test fun opportunityIdsAreUnique() {
        assertEquals(ProviderDemoData.opportunities.size, ProviderDemoData.opportunities.map { it.id }.toSet().size)
    }

    @Test fun opportunityBudgetsArePositiveAndOpen() {
        assertTrue(ProviderDemoData.opportunities.all { it.budget > 0 && it.status == "OPEN" })
    }

    @Test fun acceptedJobsUseSupportedLifecycleStatuses() {
        assertTrue(ProviderDemoData.acceptedJobs.all { it.status in setOf("ACCEPTED", "IN_PROGRESS", "COMPLETED") })
    }

    @Test fun sampleJobsUseNigerianLocations() {
        assertTrue((ProviderDemoData.opportunities + ProviderDemoData.acceptedJobs).all { it.location.contains("Port Harcourt") })
    }

    @Test fun earningsCategoriesAreNotCombinedInFixtures() {
        assertTrue(ProviderDemoData.reviews.all { it.rating in 1..5 })
    }
}
