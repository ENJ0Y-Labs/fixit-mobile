package com.fixit.mobile

import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.model.WorkspaceCatalog
import org.junit.Assert.assertTrue
import org.junit.Test

class WorkspaceCatalogTest {
    @Test fun customerWorkspaceHasCoreAreas() { assertTrue(WorkspaceCatalog.sections(Workspace.CUSTOMER).map { it.title }.containsAll(listOf("Home", "My jobs", "Post a job", "Payments", "Account"))) }
    @Test fun providerWorkspaceHasCoreAreas() { assertTrue(WorkspaceCatalog.sections(Workspace.PROVIDER).map { it.title }.containsAll(listOf("Dashboard", "Profile", "Services", "Find jobs", "My jobs", "Earnings"))) }
    @Test fun adminWorkspaceHasAllSpecifiedAreas() { assertTrue(WorkspaceCatalog.sections(Workspace.ADMIN).map { it.title }.containsAll(listOf("Dashboard", "Customers", "Providers", "Verification", "Jobs", "Disputes & support", "Categories", "Analytics", "Settings"))) }
}
