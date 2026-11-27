package com.fixit.mobile.ui.admin

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AdminDemoDataTest {
    @Test
    fun adminFixturesHaveUniqueIds() {
        val ids = demoAdminCustomers.map { it.id } +
            demoAdminProviders.map { it.id } +
            demoVerificationCases.map { it.id } +
            demoAdminJobs.map { it.id } +
            demoSupportCases.map { it.id } +
            demoAdminCategories.map { it.id }
        assertEquals(ids.size, ids.toSet().size)
    }

    @Test
    fun fixturesCoverOperationalStatuses() {
        assertTrue(demoAdminCustomers.any { it.account == "Suspended" })
        assertTrue(demoAdminProviders.any { it.verification == "Pending review" })
        assertTrue(demoVerificationCases.any { it.status == "Approved" })
        assertTrue(demoAdminJobs.any { it.status == "AWAITING_BIDS" })
        assertTrue(demoSupportCases.any { it.status == "UNDER_REVIEW" })
        assertTrue(demoAdminCategories.any { !it.active })
    }

    @Test
    fun searchHelperIsCaseInsensitiveAndKeepsAllWhenQueryIsBlank() {
        val records = listOf("Ada Nwankwo", "Chidi Okoro", "Mariam Abdullahi")
        assertEquals(listOf("Ada Nwankwo"), filterAdminRecords("ADA", records))
        assertEquals(records, filterAdminRecords("  ", records))
    }

    @Test
    fun nairaFormatterIncludesCurrencyAndAmount() {
        assertTrue(formatAdminNaira(28000).startsWith("₦"))
        assertTrue(formatAdminNaira(28000).contains("28"))
    }
}
