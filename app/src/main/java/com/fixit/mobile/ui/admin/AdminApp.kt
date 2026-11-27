package com.fixit.mobile.ui.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fixit.mobile.ui.components.*
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.theme.*

private val adminSections = listOf(
    "Dashboard", "Customers", "Providers", "Verification", "Jobs",
    "Disputes & support", "Categories", "Analytics", "Settings", "Admin account"
)

private fun sectionIcon(label: String) = when (label) {
    "Dashboard" -> Icons.Default.Dashboard
    "Customers" -> Icons.Default.Group
    "Providers" -> Icons.Default.Person
    "Verification" -> Icons.Default.VerifiedUser
    "Jobs" -> Icons.Default.Work
    "Disputes & support" -> Icons.Default.ReportProblem
    "Categories" -> Icons.Default.Category
    "Analytics" -> Icons.Default.Analytics
    "Settings" -> Icons.Default.Settings
    else -> Icons.Default.AccountCircle
}

@Composable
fun AdminApp(onWorkspaceChange: (Workspace) -> Unit) {
    var authenticated by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("admin@fixit.local") }
    var password by remember { mutableStateOf("FixitDemo123!") }
    var loginError by remember { mutableStateOf(false) }

    if (!authenticated) {
        AdminDemoGate(
            email = email,
            onEmailChange = { email = it },
            password = password,
            onPasswordChange = { password = it },
            error = loginError,
            onLogin = {
                if (email.trim().equals("admin@fixit.local", true) && password == "FixitDemo123!") {
                    authenticated = true
                    loginError = false
                } else loginError = true
            },
            onExit = { onWorkspaceChange(Workspace.CUSTOMER) }
        )
    } else {
        AdminWorkspace(onWorkspaceChange = onWorkspaceChange, onLogout = { authenticated = false })
    }
}

@Composable
private fun AdminDemoGate(
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    error: Boolean,
    onLogin: () -> Unit,
    onExit: () -> Unit
) {
    Column(
        Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("FIXIT", color = FixItPrimary, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Text("Admin access", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = FixItInk)
        Text("Sign in to open the administration preview.", color = FixItMuted)
        Spacer(Modifier.height(20.dp))
        FixItCard {
            Text("Local demo access", fontWeight = FontWeight.SemiBold)
            Text("This is a demonstration gate only. It does not authenticate with a server or protect production admin operations.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            FixItTextField(value = email, onValueChange = onEmailChange, label = "Demo email")
            FixItTextField(value = password, onValueChange = onPasswordChange, label = "Demo password")
            if (error) Text("The demo email or password is incorrect.", color = FixItDanger)
            FixItPrimaryButton("Sign in to admin preview", onLogin, Modifier.fillMaxWidth())
            Text("Demo credentials are prefilled for local preview.", color = FixItMuted, style = MaterialTheme.typography.labelSmall)
        }
        Spacer(Modifier.height(12.dp))
        FixItSecondaryButton("Return to customer app", onExit, Modifier.fillMaxWidth())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AdminWorkspace(onWorkspaceChange: (Workspace) -> Unit, onLogout: () -> Unit) {
    var page by remember { mutableStateOf("Dashboard") }
    var moreOpen by remember { mutableStateOf(false) }
    var confirmTitle by remember { mutableStateOf("") }
    var confirmMessage by remember { mutableStateOf("") }
    var confirmAction by remember { mutableStateOf<(() -> Unit)?>(null) }

    var customers by remember { mutableStateOf(demoAdminCustomers) }
    var providers by remember { mutableStateOf(demoAdminProviders) }
    var verifications by remember { mutableStateOf(demoVerificationCases) }
    var jobs by remember { mutableStateOf(demoAdminJobs) }
    var cases by remember { mutableStateOf(demoSupportCases) }
    var categories by remember { mutableStateOf(demoAdminCategories) }

    fun ask(title: String, message: String, action: () -> Unit) {
        confirmTitle = title
        confirmMessage = message
        confirmAction = action
    }

    val primary = adminSections.take(4)
    val navItems = primary.map { FixItNavigationItem(it, sectionIcon(it)) } +
        FixItNavigationItem("More", Icons.Default.Menu)

    Scaffold(
        containerColor = FixItBackground,
        topBar = {
            FixItTopAppBar(
                title = "FixIt Admin",
                subtitle = "DEMO ADMINISTRATION",
                actions = {
                    IconButton(onClick = { onWorkspaceChange(Workspace.CUSTOMER) }) {
                        Icon(Icons.Default.HomeWork, contentDescription = "Exit admin workspace")
                    }
                }
            )
        },
        bottomBar = {
            FixItBottomNavigation(
                items = navItems,
                selectedLabel = if (page in primary) page else "More",
                onSelect = { selected -> if (selected == "More") moreOpen = true else page = selected }
            )
        }
    ) { inset ->
        Column(Modifier.fillMaxSize().padding(inset)) {
            Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                FixItStatusBadge("LOCAL DEMO")
                Spacer(Modifier.width(8.dp))
                Text("Sample records only · no live admin API", color = FixItMuted, style = MaterialTheme.typography.labelSmall)
            }
            when (page) {
                "Dashboard" -> AdminDashboard(
                    customers.size, providers.size, jobs.size,
                    verifications.count { it.status == "Pending review" },
                    jobs.take(4), onNavigate = { page = it }
                )
                "Customers" -> AdminCustomers(customers, onToggle = { customer ->
                    ask(
                        if (customer.account == "Active") "Suspend customer?" else "Reactivate customer?",
                        "This changes only the local demo record for ${customer.name}. No account access is changed on the platform."
                    ) {
                        customers = customers.map { if (it.id == customer.id) it.copy(account = if (it.account == "Active") "Suspended" else "Active") else it }
                    }
                })
                "Providers" -> AdminProviders(providers, onToggle = { provider ->
                    ask(
                        if (provider.account == "Active") "Suspend provider?" else "Reactivate provider?",
                        "This changes only the local demo record for ${provider.name}. No provider access is changed on the platform."
                    ) {
                        providers = providers.map { if (it.id == provider.id) it.copy(account = if (it.account == "Active") "Suspended" else "Active") else it }
                    }
                })
                "Verification" -> AdminVerification(verifications, onDecision = { item, decision ->
                    ask("Confirm verification decision", "Mark ${item.provider}'s sample application as $decision? This does not approve or reject a real provider.") {
                        verifications = verifications.map { if (it.id == item.id) it.copy(status = decision) else it }
                    }
                })
                "Jobs" -> AdminJobs(jobs, onStatus = { job, status ->
                    ask("Change job status?", "Update sample job ${job.id} to $status? This does not change the real marketplace job.") {
                        jobs = jobs.map { if (it.id == job.id) it.copy(status = status) else it }
                    }
                })
                "Disputes & support" -> AdminSupport(cases, onStatus = { item, status ->
                    ask("Update case status?", "Move sample case ${item.id} to $status? No customer or provider will be notified.") {
                        cases = cases.map { if (it.id == item.id) it.copy(status = status) else it }
                    }
                })
                "Categories" -> AdminCategories(categories, onToggle = { item ->
                    ask(if (item.active) "Deactivate category?" else "Activate category?", "Change the local demo status for ${item.name}? Customer discovery and provider listings are not affected.") {
                        categories = categories.map { if (it.id == item.id) it.copy(active = !it.active) else it }
                    }, onCreate = { name, description ->
                        categories = categories + AdminCategory("CAT-%03d".format(categories.size + 1), name, description, 0, 0, true)
                    })
                }
                "Analytics" -> AdminAnalytics(jobs.size, customers.size, providers.size, cases.size)
                "Settings" -> AdminSettings()
                else -> AdminAccount(onLogout = onLogout)
            }
        }
    }

    if (moreOpen) {
        ModalBottomSheet(onDismissRequest = { moreOpen = false }, containerColor = FixItSurface) {
            Text("All admin sections", Modifier.padding(horizontal = 22.dp, vertical = 8.dp), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            adminSections.drop(4).forEach { item ->
                ListItem(
                    headlineContent = { Text(item) },
                    leadingContent = { Icon(sectionIcon(item), contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ListItemDefaults.colors(containerColor = FixItSurface),
                    trailingContent = { Text("Open", color = FixItPrimary, style = MaterialTheme.typography.labelMedium) }
                )
                TextButton(onClick = { page = item; moreOpen = false }, modifier = Modifier.fillMaxWidth()) { Text("Open $item") }
                HorizontalDivider(color = FixItBorder)
            }
            Spacer(Modifier.height(20.dp))
        }
    }

    if (confirmAction != null) {
        AlertDialog(
            onDismissRequest = { confirmAction = null },
            title = { Text(confirmTitle, fontWeight = FontWeight.SemiBold) },
            text = { Text(confirmMessage) },
            confirmButton = {
                TextButton(onClick = {
                    confirmAction?.invoke()
                    confirmAction = null
                }) { Text("Confirm", color = FixItDanger) }
            },
            dismissButton = { TextButton(onClick = { confirmAction = null }) { Text("Cancel") } },
            containerColor = FixItSurface
        )
    }
}

@Composable
private fun AdminPageFrame(title: String, subtitle: String, content: @Composable ColumnScope.() -> Unit) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 16.dp, vertical = 12.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
            Text(subtitle, color = FixItMuted, style = MaterialTheme.typography.bodyMedium)
        }
        content()
        Text("Illustrative data · changes remain in this app session", color = FixItMuted, style = MaterialTheme.typography.labelSmall)
        Spacer(Modifier.height(12.dp))
    }
}

@Composable
private fun DemoNotice(message: String = "This workspace uses sample records. It is not connected to the production backend.") {
    FixItCard {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("DEMO", color = FixItPrimary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.labelMedium)
            Text(message, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
        }
    }
}

@Composable
private fun AdminMetric(label: String, value: String, note: String) {
    FixItCard(Modifier.widthIn(min = 140.dp)) {
        Text(label, color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
        Text(note, color = FixItMuted, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun AdminSearch(value: String, onChange: (String) -> Unit, placeholder: String = "Search records") {
    OutlinedTextField(
        value = value, onValueChange = onChange, modifier = Modifier.fillMaxWidth(),
        label = { Text(placeholder) }, singleLine = true
    )
}

@Composable
private fun AdminFilter(options: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        options.forEach { option ->
            FilterChip(selected = selected == option, onClick = { onSelect(option) }, label = { Text(option) })
        }
    }
}

@Composable
private fun AdminRecordCard(title: String, subtitle: String, detail: String, status: String, onClick: () -> Unit) {
    FixItCard {
        Column(Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(title, Modifier.weight(1f), fontWeight = FontWeight.SemiBold, color = FixItInk)
                FixItStatusBadge(status)
            }
            Text(subtitle, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            Text(detail, color = FixItMuted, style = MaterialTheme.typography.labelSmall)
            TextButton(onClick = onClick, modifier = Modifier.align(Alignment.End)) { Text("View details") }
        }
    }
}

@Composable
private fun AdminDashboard(
    customerCount: Int, providerCount: Int, jobCount: Int, pending: Int,
    recentJobs: List<AdminJob>, onNavigate: (String) -> Unit
) {
    AdminPageFrame("Platform overview", "Monitor marketplace activity and operational queues.") {
        DemoNotice("All metrics and activity below are illustrative, not live platform reporting.")
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AdminMetric("Customers", customerCount.toString(), "Sample records")
            AdminMetric("Providers", providerCount.toString(), "Sample records")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AdminMetric("Jobs", jobCount.toString(), "Sample jobs")
            AdminMetric("Verification", pending.toString(), "Awaiting review")
        }
        FixItCard {
            Text("Needs attention", fontWeight = FontWeight.Bold)
            AdminQuickAction("Review provider applications", "$pending pending sample cases") { onNavigate("Verification") }
            AdminQuickAction("Open disputes and support", "Review sample operational cases") { onNavigate("Disputes & support") }
            AdminQuickAction("Manage service categories", "Review active and inactive categories") { onNavigate("Categories") }
        }
        FixItCard {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Recent job activity", Modifier.weight(1f), fontWeight = FontWeight.Bold)
                TextButton(onClick = { onNavigate("Jobs") }) { Text("View all") }
            }
            recentJobs.forEach { job ->
                HorizontalDivider(color = FixItBorder)
                Text(job.id + " · " + job.title, fontWeight = FontWeight.Medium)
                Text("${job.customer} · ${job.status.replace('_', ' ')}", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun AdminQuickAction(title: String, subtitle: String, onClick: () -> Unit) {
    TextButton(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            Text(title, color = FixItInk, fontWeight = FontWeight.SemiBold)
            Text(subtitle, color = FixItMuted, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
private fun AdminCustomers(records: List<AdminCustomer>, onToggle: (AdminCustomer) -> Unit) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var selected by remember { mutableStateOf<AdminCustomer?>(null) }
    val filtered = records.filter {
        (filter == "All" || it.account == filter) &&
            listOf(it.id, it.name, it.email, it.location).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Customers", "Search customer records and review account status.") {
        AdminSearch(query, { query = it }, "Search name, email, ID or location")
        AdminFilter(listOf("All", "Active", "Suspended"), filter) { filter = it }
        Text("${filtered.size} sample customers", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        if (filtered.isEmpty()) FixItEmptyState("No customers found", "Try another search or account filter.")
        filtered.forEach { item ->
            AdminRecordCard(item.name, item.id + " · " + item.email, "${item.location} · ${item.jobs} jobs", item.account) { selected = item }
        }
    }
    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(item.name) },
            text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.id); Text(item.email); Text(item.phone); Text(item.location); Text("Jobs: ${item.jobs}"); Text("Account: ${item.account}")
                Text("Read-only sample details. No customer API is connected.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            } },
            confirmButton = {
                TextButton(onClick = { onToggle(item); selected = null }) {
                    Text(if (item.account == "Active") "Suspend account" else "Reactivate account")
                }
            },
            dismissButton = { TextButton(onClick = { selected = null }) { Text("Close") } }
        )
    }
}

@Composable
private fun AdminProviders(records: List<AdminProvider>, onToggle: (AdminProvider) -> Unit) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var selected by remember { mutableStateOf<AdminProvider?>(null) }
    val filtered = records.filter {
        (filter == "All" || when (filter) {
            "Verified" -> it.verification == "Verified"
            "Pending" -> it.verification == "Pending review"
            "Suspended" -> it.account == "Suspended"
            else -> true
        }) && listOf(it.id, it.name, it.business, it.category, it.location).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Providers", "Review provider profiles, service categories and account status.") {
        AdminSearch(query, { query = it }, "Search provider, business, category or ID")
        AdminFilter(listOf("All", "Verified", "Pending", "Suspended"), filter) { filter = it }
        Text("${filtered.size} sample providers", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        if (filtered.isEmpty()) FixItEmptyState("No providers found", "Try another search or filter.")
        filtered.forEach { item ->
            AdminRecordCard(item.name, item.business + " · " + item.id, "${item.category} · ${item.location} · ★ ${item.rating}", item.account) { selected = item }
        }
    }
    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(item.name) },
            text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.id); Text(item.business); Text(item.category); Text(item.location)
                Text("Rating: ${item.rating} · Jobs: ${item.jobs}")
                Text("Verification: ${item.verification}"); Text("Account: ${item.account}")
                Text("Sample provider profile. No live provider record is changed.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            } },
            confirmButton = {
                TextButton(onClick = { onToggle(item); selected = null }) {
                    Text(if (item.account == "Active") "Suspend account" else "Reactivate account")
                }
            },
            dismissButton = { TextButton(onClick = { selected = null }) { Text("Close") } }
        )
    }
}

@Composable
private fun AdminVerification(records: List<VerificationCase>, onDecision: (VerificationCase, String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var selected by remember { mutableStateOf<VerificationCase?>(null) }
    val filtered = records.filter {
        (filter == "All" || it.status == filter) &&
            listOf(it.id, it.provider, it.business, it.service).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Provider verification", "Review submitted evidence metadata and sample application decisions.") {
        DemoNotice("Document names are metadata only. No private documents are attached or available to inspect.")
        AdminSearch(query, { query = it }, "Search provider, service or case ID")
        AdminFilter(listOf("All", "Pending review", "Needs information", "Approved", "Rejected"), filter) { filter = it }
        Text("${filtered.size} sample applications", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        filtered.forEach { item ->
            AdminRecordCard(item.provider, item.id + " · " + item.business, "${item.service} · ${item.location}", item.status) { selected = item }
        }
    }
    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text("Verification · ${item.id}") },
            text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.provider, fontWeight = FontWeight.SemiBold)
                Text(item.business + " · " + item.service)
                Text(item.location)
                item.documents.forEach { (name, status) ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(name, modifier = Modifier.weight(1f), style = MaterialTheme.typography.bodySmall)
                        FixItStatusBadge(status)
                    }
                }
                Text("Current status: ${item.status}", fontWeight = FontWeight.SemiBold)
            } },
            confirmButton = {
                Row {
                    TextButton(onClick = { onDecision(item, "Approved"); selected = null }) { Text("Approve") }
                    TextButton(onClick = { onDecision(item, "Rejected"); selected = null }) { Text("Reject", color = FixItDanger) }
                }
            },
            dismissButton = {
                TextButton(onClick = { onDecision(item, "Needs information"); selected = null }) { Text("Request info") }
            }
        )
    }
}

@Composable
private fun AdminJobs(records: List<AdminJob>, onStatus: (AdminJob, String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var selected by remember { mutableStateOf<AdminJob?>(null) }
    val filtered = records.filter {
        (filter == "All" || it.status == filter) &&
            listOf(it.id, it.title, it.category, it.customer, it.provider, it.location).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Jobs", "Monitor marketplace jobs and inspect their current status.") {
        AdminSearch(query, { query = it }, "Search job, customer, provider or location")
        AdminFilter(listOf("All", "AWAITING_BIDS", "IN_PROGRESS", "COMPLETED", "CANCELLED"), filter) { filter = it }
        Text("${filtered.size} sample jobs", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        filtered.forEach { item ->
            AdminRecordCard(item.title, item.id + " · " + item.category, "${item.customer} · ${formatAdminNaira(item.budget)}", item.status.replace('_', ' ')) { selected = item }
        }
    }
    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(item.title) },
            text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.id + " · " + item.category)
                Text("Customer: ${item.customer}")
                Text("Provider: ${item.provider}")
                Text(item.location)
                Text("Budget: ${formatAdminNaira(item.budget)} · Bids: ${item.bids}")
                Text("Status: ${item.status}")
            } },
            confirmButton = {
                TextButton(onClick = { onStatus(item, "IN_PROGRESS"); selected = null }) { Text("Set in progress") }
                TextButton(onClick = { onStatus(item, "COMPLETED"); selected = null }) { Text("Complete") }
            },
            dismissButton = {
                TextButton(onClick = { onStatus(item, "CANCELLED"); selected = null }) { Text("Cancel job", color = FixItDanger) }
            }
        )
    }
}

@Composable
private fun AdminSupport(records: List<SupportCase>, onStatus: (SupportCase, String) -> Unit) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var selected by remember { mutableStateOf<SupportCase?>(null) }
    val filtered = records.filter {
        (filter == "All" || it.status == filter) &&
            listOf(it.id, it.type, it.subject, it.customer, it.provider).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Disputes & support", "Review sample cases, priorities and case status.") {
        DemoNotice("Case timelines and evidence are illustrative. This preview cannot send replies, issue refunds or contact users.")
        AdminSearch(query, { query = it }, "Search case, subject or participant")
        AdminFilter(listOf("All", "OPEN", "UNDER_REVIEW", "WAITING_CUSTOMER", "WAITING_PROVIDER", "RESOLVED", "CLOSED"), filter) { filter = it }
        Text("${filtered.size} sample cases", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        filtered.forEach { item ->
            AdminRecordCard(item.subject, item.id + " · " + item.type + " · ${item.priority}", "Customer: ${item.customer} · Provider: ${item.provider}", item.status) { selected = item }
        }
    }
    selected?.let { item ->
        AlertDialog(
            onDismissRequest = { selected = null },
            title = { Text(item.id + " · " + item.type) },
            text = { Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(item.subject, fontWeight = FontWeight.SemiBold)
                Text("Priority: ${item.priority}")
                Text("Customer: ${item.customer}")
                Text("Provider: ${item.provider}")
                Text(item.summary)
                Text("Current status: ${item.status}")
            } },
            confirmButton = {
                TextButton(onClick = { onStatus(item, "RESOLVED"); selected = null }) { Text("Mark resolved") }
                TextButton(onClick = { onStatus(item, "UNDER_REVIEW"); selected = null }) { Text("Review") }
            },
            dismissButton = { TextButton(onClick = { onStatus(item, "CLOSED"); selected = null }) { Text("Close case") } }
        )
    }
}

@Composable
private fun AdminCategories(
    records: List<AdminCategory>,
    onToggle: (AdminCategory) -> Unit,
    onCreate: (String, String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var filter by remember { mutableStateOf("All") }
    var showCreate by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val filtered = records.filter {
        (filter == "All" || (if (filter == "Active") it.active else !it.active)) &&
            listOf(it.name, it.id, it.description).any { field -> field.contains(query, true) }
    }
    AdminPageFrame("Categories", "Manage service categories in the local demonstration workspace.") {
        AdminSearch(query, { query = it }, "Search categories")
        AdminFilter(listOf("All", "Active", "Inactive"), filter) { filter = it }
        FixItPrimaryButton("Add category", { showCreate = true }, Modifier.fillMaxWidth())
        filtered.forEach { item ->
            FixItCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.name, Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
                    FixItStatusBadge(if (item.active) "Active" else "Inactive")
                }
                Text(item.id + " · ${item.providers} providers · ${item.jobs} jobs", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                Text(item.description, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                TextButton(onClick = { onToggle(item) }, modifier = Modifier.align(Alignment.End)) {
                    Text(if (item.active) "Deactivate" else "Activate")
                }
            }
        }
    }
    if (showCreate) {
        AlertDialog(
            onDismissRequest = { showCreate = false },
            title = { Text("Create sample category") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    FixItTextField(name, { name = it }, "Category name")
                    FixItTextField(description, { description = it }, "Description", singleLine = false)
                    Text("This creates a local sample record only.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                }
            },
            confirmButton = {
                TextButton(enabled = name.isNotBlank(), onClick = {
                    onCreate(name.trim(), description.trim())
                    name = ""; description = ""; showCreate = false
                }) { Text("Create") }
            },
            dismissButton = { TextButton(onClick = { showCreate = false }) { Text("Cancel") } }
        )
    }
}

@Composable
private fun AdminAnalytics(customerCount: Int, providerCount: Int, jobCount: Int, caseCount: Int) {
    var period by remember { mutableStateOf("30 days") }
    val multiplier = when (period) { "7 days" -> 1; "90 days" -> 3; else -> 2 }
    val jobsValue = jobCount * 124
    AdminPageFrame("Analytics", "Sample marketplace activity and operational reporting.") {
        DemoNotice("All figures are illustrative. Estimated job value is not revenue, provider earnings or payments.")
        AdminFilter(listOf("7 days", "30 days", "90 days"), period) { period = it }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AdminMetric("Jobs created", (jobCount * multiplier).toString(), period)
            AdminMetric("Jobs completed", (jobCount * multiplier * 0.62).toInt().toString(), "Sample estimate")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            AdminMetric("New customers", (customerCount * multiplier).toString(), period)
            AdminMetric("New providers", (providerCount * multiplier / 2).toString(), period)
        }
        FixItCard {
            Text("Job status breakdown", fontWeight = FontWeight.Bold)
            listOf("Completed" to 624, "Awaiting bids" to 148, "In progress" to 96, "Cancelled" to 52).forEach { (label, count) ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(label, style = MaterialTheme.typography.bodySmall)
                        Text(count.toString(), fontWeight = FontWeight.SemiBold)
                    }
                    LinearProgressIndicator(progress = { count / 624f }, modifier = Modifier.fillMaxWidth(), color = FixItPrimary, trackColor = FixItSurfaceSoft)
                }
            }
        }
        FixItCard {
            Text("Top service categories", fontWeight = FontWeight.Bold)
            listOf("Cleaning" to 182, "Plumbing" to 126, "Electrical" to 104, "Laundry" to 91, "AC repair" to 76).forEach { (label, count) ->
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(label); Text("$count jobs", color = FixItMuted)
                }
            }
        }
        AdminMetric("Open sample cases", caseCount.toString(), "Not live support data")
        Text("Estimated sample job value: ${formatAdminNaira(jobsValue * multiplier)}. This is based on illustrative budget assumptions and must not be treated as financial reporting.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun AdminSettings() {
    var emailAlerts by remember { mutableStateOf(true) }
    var verificationAlerts by remember { mutableStateOf(true) }
    var disputeAlerts by remember { mutableStateOf(true) }
    var weeklySummary by remember { mutableStateOf(false) }
    var compactTables by remember { mutableStateOf(false) }
    var timezone by remember { mutableStateOf("Africa/Lagos") }
    var dateFormat by remember { mutableStateOf("DD/MM/YYYY") }
    var feedback by remember { mutableStateOf("") }
    AdminPageFrame("Settings", "Manage temporary preferences for this admin preview.") {
        DemoNotice("Settings are held in local app state. No production configuration is changed.")
        FixItCard {
            Text("Notification preferences", fontWeight = FontWeight.Bold)
            AdminToggle("Email notifications", emailAlerts) { emailAlerts = it }
            AdminToggle("Provider verification alerts", verificationAlerts) { verificationAlerts = it }
            AdminToggle("Disputes and support alerts", disputeAlerts) { disputeAlerts = it }
            AdminToggle("Weekly activity summary", weeklySummary) { weeklySummary = it }
        }
        FixItCard {
            Text("Regional and display preferences", fontWeight = FontWeight.Bold)
            FixItDropdown("Time zone", listOf("Africa/Lagos", "UTC"), timezone, { timezone = it })
            FixItDropdown("Date format", listOf("DD/MM/YYYY", "MM/DD/YYYY", "YYYY-MM-DD"), dateFormat, { dateFormat = it })
            AdminToggle("Compact tables", compactTables) { compactTables = it }
        }
        FixItCard {
            Text("Platform information", fontWeight = FontWeight.Bold)
            AdminInfo("Application", "FixIt Admin")
            AdminInfo("Environment", "Local demo")
            AdminInfo("Data source", "Illustrative sample data")
            AdminInfo("Backend connection", "Not configured")
            AdminInfo("Admin API", "Proposed routes only, not connected")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FixItSecondaryButton("Reset", {
                emailAlerts = true; verificationAlerts = true; disputeAlerts = true
                weeklySummary = false; compactTables = false; timezone = "Africa/Lagos"
                dateFormat = "DD/MM/YYYY"; feedback = "Demo preferences reset."
            }, Modifier.weight(1f))
            FixItPrimaryButton("Apply", { feedback = "Demo preferences applied for this session only." }, Modifier.weight(1f))
        }
        if (feedback.isNotBlank()) Text(feedback, color = FixItSuccess, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun AdminToggle(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(label, Modifier.weight(1f), style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun AdminInfo(label: String, value: String) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
        Text(value, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
    }
}

@Composable
private fun AdminAccount(onLogout: () -> Unit) {
    AdminPageFrame("Admin account", "Read-only information about the current local demo identity.") {
        DemoNotice("No production admin session is active. Account and security operations require an approved backend authentication contract.")
        FixItCard {
            Text("Admin workspace", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            FixItStatusBadge("Demo administrator")
            AdminInfo("Display name", "Admin workspace")
            AdminInfo("Email", "admin@fixit.local")
            AdminInfo("Role", "Demo administrator")
            AdminInfo("Session", "Local preview only")
            AdminInfo("Account status", "Not connected")
        }
        FixItCard {
            Text("Account security", fontWeight = FontWeight.Bold)
            Text("Password changes, multi-factor authentication, active-session management, role changes and audit history are unavailable because no authenticated admin backend is connected.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
        }
        FixItSecondaryButton("Sign out of local preview", onLogout, Modifier.fillMaxWidth())
    }
}
