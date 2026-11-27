package com.fixit.mobile.ui.provider

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import com.fixit.mobile.ui.components.*
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.theme.FixItInk
import com.fixit.mobile.ui.theme.FixItMuted
import com.fixit.mobile.ui.theme.FixItPrimary
import java.text.NumberFormat
import java.util.Locale

private val ngn = NumberFormat.getCurrencyInstance(Locale("en", "NG"))

@Composable
fun ProviderApp(onWorkspaceChange: (Workspace) -> Unit) {
    var page by remember { mutableStateOf("Dashboard") }
    var profileName by remember { mutableStateOf("Provider Demo") }
    var phone by remember { mutableStateOf("+234 800 000 0000") }
    var email by remember { mutableStateOf("provider@fixit.local") }
    var bio by remember { mutableStateOf("Reliable home service professional serving Port Harcourt.") }
    var area by remember { mutableStateOf("Port Harcourt, Rivers State") }
    var services by remember { mutableStateOf(ProviderDemoData.services) }
    var availability by remember { mutableStateOf(ProviderDemoData.weekdays.mapIndexed { i, day -> day to (i < 5) }.toMap()) }
    var acceptingWork by remember { mutableStateOf(true) }
    var opportunities by remember { mutableStateOf(ProviderDemoData.opportunities) }
    var acceptedJobs by remember { mutableStateOf(ProviderDemoData.acceptedJobs) }
    var bids by remember { mutableStateOf(emptyList<ProviderBid>()) }
    var selectedJob by remember { mutableStateOf<ProviderJob?>(null) }
    var notice by remember { mutableStateOf("") }
    var settingsNotifications by remember { mutableStateOf(true) }
    var settingsEmail by remember { mutableStateOf(false) }

    val destinations = listOf("Dashboard", "Available Jobs", "My Jobs", "Profile", "Services", "Availability", "Earnings", "Reviews", "Settings")
    Scaffold(
        topBar = {
            FixItTopAppBar(
                title = if (selectedJob != null) "Job details" else page,
                subtitle = "Provider workspace",
                actions = {
                    TextButton(onClick = { onWorkspaceChange(Workspace.CUSTOMER) }) { Text("Customer") }
                    TextButton(onClick = { onWorkspaceChange(Workspace.ADMIN) }) { Text("Admin") }
                }
            )
        },
        bottomBar = {
            FixItBottomNavigation(
                items = listOf(
                    FixItNavigationItem("Home", Icons.Filled.Home),
                    FixItNavigationItem("Find jobs", Icons.Filled.Search),
                    FixItNavigationItem("My jobs", Icons.Filled.Work),
                    FixItNavigationItem("Account", Icons.Filled.Person)
                ),
                selectedLabel = when (page) {
                    "Dashboard" -> "Home"
                    "Available Jobs" -> "Find jobs"
                    "My Jobs" -> "My jobs"
                    else -> "Account"
                },
                onSelect = { page = when (it) { "Home" -> "Dashboard"; "Find jobs" -> "Available Jobs"; "My jobs" -> "My Jobs"; else -> "Profile" }; selectedJob = null }
            )
        }
    ) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            if (selectedJob != null) {
                ProviderJobDetail(
                    job = selectedJob!!,
                    existingBid = bids.firstOrNull { it.jobId == selectedJob!!.id },
                    onBack = { selectedJob = null },
                    onBid = { amount, message, schedule ->
                        val job = selectedJob!!
                        bids = bids.filterNot { it.jobId == job.id } + ProviderBid("demo-bid-${bids.size + 1}", job.id, amount, message, schedule)
                        opportunities = opportunities.map { if (it.id == job.id) it.copy(responses = it.responses + 1) else it }
                        notice = "Demo quote added locally. It was not sent to a customer."
                    }
                )
            } else {
                when (page) {
                    "Dashboard" -> ProviderDashboard(profileName, opportunities, acceptedJobs, bids, onNavigate = { page = it })
                    "Available Jobs" -> AvailableJobsScreen(opportunities, bids, onSelect = { selectedJob = it })
                    "My Jobs" -> ProviderMyJobs(acceptedJobs, bids, onSelect = { selectedJob = it }, onProgress = { job, status, note ->
                        acceptedJobs = acceptedJobs.map { if (it.id == job.id) it.copy(status = status, progressNote = note) else it }
                        notice = "Demo job status updated locally."
                    }, notice = notice)
                    "Profile" -> ProviderProfile(profileName, phone, email, bio, area, onSave = { n, p, e, b, a ->
                        profileName = n; phone = p; email = e; bio = b; area = a
                        notice = "Profile draft updated in this session only."
                    })
                    "Services" -> ProviderServices(services, onChange = { services = it })
                    "Availability" -> ProviderAvailability(availability, acceptingWork, onToggle = { acceptingWork = it }, onChange = { availability = it })
                    "Earnings" -> ProviderEarnings()
                    "Reviews" -> ProviderReviews()
                    "Settings" -> ProviderSettings(settingsNotifications, settingsEmail, onNotifications = { settingsNotifications = it }, onEmail = { settingsEmail = it })
                    else -> ProviderDashboard(profileName, opportunities, acceptedJobs, bids, onNavigate = { page = it })
                }
            }
        }
    }
}

@Composable
private fun ProviderDashboard(name: String, jobs: List<ProviderJob>, accepted: List<ProviderJob>, bids: List<ProviderBid>, onNavigate: (String) -> Unit) {
    val active = accepted.count { it.status == "IN_PROGRESS" }
    val available = jobs.count { it.status == "OPEN" }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Good day, $name", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
            Text("Your service business at a glance. These figures are sample data.", color = FixItMuted)
        }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Open jobs", available.toString(), Modifier.weight(1f))
                MetricCard("Active work", active.toString(), Modifier.weight(1f))
            }
            Spacer(Modifier.height(8.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricCard("Quotes", bids.size.toString(), Modifier.weight(1f))
                MetricCard("Accepted", accepted.size.toString(), Modifier.weight(1f))
            }
        }
        item { SectionTitle("Your workspace", "Quick access to provider tools") }
        items(listOf("Available Jobs", "My Jobs", "Profile", "Services", "Availability", "Earnings", "Reviews", "Settings")) { label ->
            FixItCard(Modifier.clickable { onNavigate(label) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(label, fontWeight = FontWeight.SemiBold, color = FixItInk)
                        Text(when (label) {
                            "Available Jobs" -> "Find service requests that match your work"
                            "My Jobs" -> "Track accepted jobs and progress"
                            "Services" -> "Manage what you offer and your pricing"
                            "Availability" -> "Set working days and hours"
                            else -> "Open $label"
                        }, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                    }
                    Text("›", color = FixItPrimary, style = MaterialTheme.typography.headlineSmall)
                }
            }
        }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun MetricCard(label: String, value: String, modifier: Modifier = Modifier) {
    FixItCard(modifier) {
        Text(value, style = MaterialTheme.typography.headlineMedium, color = FixItPrimary, fontWeight = FontWeight.Bold)
        Text(label, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun AvailableJobsScreen(jobs: List<ProviderJob>, bids: List<ProviderBid>, onSelect: (ProviderJob) -> Unit) {
    var query by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("All categories") }
    var budget by remember { mutableStateOf("Any budget") }
    val categories = listOf("All categories") + jobs.map { it.category }.distinct()
    val filtered = jobs.filter { job ->
        job.status == "OPEN" &&
            (query.isBlank() || listOf(job.title, job.description, job.location, job.category).any { it.contains(query, true) }) &&
            (category == "All categories" || job.category == category) &&
            when (budget) {
                "Under ₦20,000" -> job.budget < 20000
                "₦20,000–₦40,000" -> job.budget in 20000..40000
                "Over ₦40,000" -> job.budget > 40000
                else -> true
            }
    }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Find your next job", color = FixItMuted)
            FixItTextField(query, { query = it }, "Search jobs", placeholder = "Try plumbing or Rumuola")
            FixItDropdown("Category", categories, category, { category = it })
            FixItDropdown("Budget", listOf("Any budget", "Under ₦20,000", "₦20,000–₦40,000", "Over ₦40,000"), budget, { budget = it })
            Text("${filtered.size} demo opportunities", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        }
        if (filtered.isEmpty()) item { FixItEmptyState("No matching jobs", "Change your search or clear the filters.") }
        items(filtered, key = { it.id }) { job ->
            FixItCard(Modifier.clickable { onSelect(job) }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(job.category, color = FixItPrimary, fontWeight = FontWeight.SemiBold, modifier = Modifier.weight(1f))
                    FixItStatusBadge("OPEN")
                }
                Text(job.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = FixItInk)
                Text(job.location, color = FixItMuted)
                Text("${ngn.format(job.budget)} · ${job.urgency} · ${job.duration}", fontWeight = FontWeight.Medium)
                Text("${job.responses} responses · ${job.posted}", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                if (bids.any { it.jobId == job.id }) FixItStatusBadge("Quote drafted locally")
                FixItSecondaryButton("View job and quote", { onSelect(job) }, Modifier.fillMaxWidth())
            }
        }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun ProviderJobDetail(job: ProviderJob, existingBid: ProviderBid?, onBack: () -> Unit, onBid: (Int, String, String) -> Unit) {
    var amount by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var schedule by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FixItSecondaryButton("Back to available jobs", onBack, Modifier.fillMaxWidth())
            FixItCard {
                FixItStatusBadge("OPEN OPPORTUNITY")
                Text(job.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = FixItInk)
                Text(job.category, color = FixItPrimary, fontWeight = FontWeight.SemiBold)
                Text(job.description, color = FixItMuted)
                Text("General area: ${job.location}", fontWeight = FontWeight.Medium)
                Text("Customer details are protected. Name, phone and exact street address are not shown before a job is awarded.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                Divider()
                Text("Indicative budget", color = FixItMuted)
                Text(ngn.format(job.budget), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = FixItInk)
                Text("${job.urgency} · ${job.duration} · ${job.responses} responses", color = FixItMuted)
            }
        }
        item {
            FixItCard {
                Text("Submit a quote", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                if (existingBid != null) {
                    FixItStatusBadge(existingBid.status)
                    Text("Your demo quote: ${ngn.format(existingBid.amount)}")
                    Text(existingBid.message, color = FixItMuted)
                    Text("This quote is stored only in the current app session.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                } else {
                    FixItTextField(amount, { amount = it.filter(Char::isDigit); error = "" }, "Your price (₦)", placeholder = "e.g. 20000", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                    FixItTextField(message, { message = it; error = "" }, "Explain your quote", placeholder = "Describe the work and materials", singleLine = false)
                    FixItTextField(schedule, { schedule = it; error = "" }, "Proposed schedule (optional)", placeholder = "e.g. 28 Nov, 10:00 AM")
                    if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
                    FixItPrimaryButton("Save demo quote", {
                        val value = amount.toIntOrNull()
                        error = when {
                            value == null || value <= 0 -> "Enter a price greater than ₦0."
                            message.isBlank() -> "Explain the work included in your quote."
                            else -> ""
                        }
                        if (error.isEmpty()) onBid(value!!, message.trim(), schedule.trim())
                    }, Modifier.fillMaxWidth())
                    Text("A quote is only a proposal. This demo does not send it to a customer.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                }
            }
            ProviderDemoNotice()
        }
    }
}

@Composable
private fun ProviderMyJobs(jobs: List<ProviderJob>, bids: List<ProviderBid>, onSelect: (ProviderJob) -> Unit, onProgress: (ProviderJob, String, String) -> Unit, notice: String) {
    var filter by remember { mutableStateOf("All") }
    val filtered = jobs.filter { filter == "All" || it.status.replace("_", " ") == filter }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Work you've accepted", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Submitted quotes are not accepted jobs. Only customer-awarded work appears in the accepted jobs list.", color = FixItMuted)
            FixItDropdown("Status", listOf("All", "ACCEPTED", "IN PROGRESS", "COMPLETED"), filter, { filter = it })
            if (notice.isNotBlank()) Text(notice, color = FixItPrimary)
        }
        items(filtered, key = { it.id }) { job ->
            FixItCard {
                Text(job.title, fontWeight = FontWeight.Bold, color = FixItInk)
                Text(job.location, color = FixItMuted)
                FixItStatusBadge(job.status.replace("_", " "))
                Text("Agreed amount: ${ngn.format(job.budget)}")
                if (job.progressNote.isNotBlank()) Text("Latest update: ${job.progressNote}", color = FixItMuted)
                FixItSecondaryButton("View job", { onSelect(job) }, Modifier.fillMaxWidth())
                when (job.status) {
                    "ACCEPTED" -> FixItPrimaryButton("Start work", { onProgress(job, "IN_PROGRESS", "Work started in demo mode.") }, Modifier.fillMaxWidth())
                    "IN_PROGRESS" -> FixItPrimaryButton("Mark work completed", { onProgress(job, "COMPLETED", "Marked completed in demo mode.") }, Modifier.fillMaxWidth())
                }
            }
        }
        if (filtered.isEmpty()) item { FixItEmptyState("No jobs in this status", "Accepted jobs will appear here after a customer awards a quote.") }
        if (bids.isNotEmpty()) item {
            SectionTitle("Your demo quotes", "A quote is not accepted work")
            bids.forEach { bid ->
                FixItCard {
                    Text("Job ${bid.jobId}", fontWeight = FontWeight.SemiBold)
                    Text(ngn.format(bid.amount))
                    FixItStatusBadge(bid.status)
                    Text(bid.message, color = FixItMuted)
                }
            }
        }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun ProviderProfile(name: String, phone: String, email: String, bio: String, area: String, onSave: (String, String, String, String, String) -> Unit) {
    var draftName by remember(name) { mutableStateOf(name) }
    var draftPhone by remember(phone) { mutableStateOf(phone) }
    var draftEmail by remember(email) { mutableStateOf(email) }
    var draftBio by remember(bio) { mutableStateOf(bio) }
    var draftArea by remember(area) { mutableStateOf(area) }
    var preview by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf("") }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            FixItCard {
                Text("How customers see you", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                FixItTextField(draftName, { draftName = it }, "Display name")
                FixItTextField(draftPhone, { draftPhone = it }, "Phone number")
                FixItTextField(draftEmail, { draftEmail = it }, "Email address")
                FixItTextField(draftArea, { draftArea = it }, "Service area")
                FixItTextField(draftBio, { draftBio = it }, "Professional biography", singleLine = false)
                if (error.isNotBlank()) Text(error, color = MaterialTheme.colorScheme.error)
                FixItPrimaryButton("Preview profile", {
                    error = when {
                        draftName.isBlank() -> "Enter your display name."
                        !draftEmail.contains("@") -> "Enter a valid email address."
                        draftPhone.isBlank() || draftArea.isBlank() -> "Phone number and service area are required."
                        else -> ""
                    }
                    if (error.isEmpty()) preview = true
                }, Modifier.fillMaxWidth())
            }
            ProviderDemoNotice()
        }
        if (preview) item {
            FixItCard {
                Text("Profile preview", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(draftName, fontWeight = FontWeight.Bold)
                Text(draftArea, color = FixItMuted)
                Text(draftBio)
                Text("Services: Plumbing", color = FixItPrimary)
                Text(draftPhone)
                Text(draftEmail)
                FixItPrimaryButton("Keep changes in this session", { onSave(draftName, draftPhone, draftEmail, draftBio, draftArea); preview = false }, Modifier.fillMaxWidth())
                FixItSecondaryButton("Back to editing", { preview = false }, Modifier.fillMaxWidth())
            }
        }
    }
}

@Composable
private fun ProviderServices(services: List<ProviderService>, onChange: (List<ProviderService>) -> Unit) {
    var addName by remember { mutableStateOf("") }
    var notice by remember { mutableStateOf("") }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Describe what you do and what customers can expect.", color = FixItMuted)
            FixItDropdown("Add a service", ProviderDemoData.categories.filter { candidate -> services.none { it.name == candidate } }.ifEmpty { listOf("All listed services added") }, addName.ifBlank { "Choose a service" }, { addName = it })
            FixItPrimaryButton("Add selected service", {
                if (addName.isNotBlank() && addName != "Choose a service" && addName != "All listed services added") {
                    onChange(services + ProviderService((services.maxOfOrNull { it.id } ?: 0) + 1, addName, "", "", "Quote after assessment"))
                    addName = ""; notice = "Service added to this session."
                }
            }, Modifier.fillMaxWidth(), enabled = addName.isNotBlank())
            if (notice.isNotBlank()) Text(notice, color = FixItPrimary)
        }
        items(services, key = { it.id }) { service ->
            var description by remember(service.id) { mutableStateOf(service.description) }
            var price by remember(service.id) { mutableStateOf(service.price) }
            var pricing by remember(service.id) { mutableStateOf(service.pricing) }
            FixItCard {
                Text(service.name, fontWeight = FontWeight.Bold)
                FixItTextField(description, { description = it }, "Description", singleLine = false)
                FixItDropdown("Pricing model", listOf("Starting from", "Fixed price", "Quote after assessment"), pricing, { pricing = it })
                if (pricing != "Quote after assessment") FixItTextField(price, { price = it.filter(Char::isDigit) }, "Price (₦)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FixItPrimaryButton("Apply draft", {
                        if (description.isBlank() || (pricing != "Quote after assessment" && (price.toIntOrNull() ?: 0) <= 0)) notice = "Add a description and a valid price."
                        else { onChange(services.map { if (it.id == service.id) it.copy(description = description, price = price, pricing = pricing) else it }); notice = "Service draft updated in this session." }
                    }, Modifier.weight(1f))
                    FixItSecondaryButton("Remove", { onChange(services.filterNot { it.id == service.id }); notice = "Service removed from this session." }, Modifier.weight(1f))
                }
            }
        }
        if (services.isEmpty()) item { FixItEmptyState("No services listed", "Add a service to describe your work.") }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun ProviderAvailability(days: Map<String, Boolean>, available: Boolean, onToggle: (Boolean) -> Unit, onChange: (Map<String, Boolean>) -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            FixItCard {
                Text("Availability", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(if (available) "Accepting new work" else "Unavailable for new work", fontWeight = FontWeight.SemiBold)
                        Text("This setting is local to the current demo session.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                    }
                    Switch(checked = available, onCheckedChange = onToggle)
                }
                Divider()
                Text("Weekly working days", fontWeight = FontWeight.SemiBold)
                ProviderDemoData.weekdays.forEach { day ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = days[day] == true, enabled = available, onCheckedChange = { checked -> onChange(days + (day to checked)) })
                        Text(day, modifier = Modifier.weight(1f))
                        Text(if (days[day] == true) "9:00 AM–5:00 PM" else "Day off", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun ProviderEarnings() {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Earnings overview", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Illustrative figures only. These are not account balances or payout records.", color = FixItMuted)
            MetricCard("Estimated", ngn.format(85000), Modifier.fillMaxWidth())
            MetricCard("Agreed", ngn.format(36000), Modifier.fillMaxWidth())
            MetricCard("Paid", ngn.format(22000), Modifier.fillMaxWidth())
            SectionTitle("Recent records", "Sample records with separate amount classifications")
        }
        items(listOf("Kitchen tap repair · Estimated · ${ngn.format(18000)}", "Wall socket repair · Agreed · ${ngn.format(22000)}", "Pump inspection · Paid · ${ngn.format(22000)}")) { row ->
            FixItCard { Text(row, fontWeight = FontWeight.Medium) }
        }
        item {
            FixItCard {
                Text("Payouts unavailable", fontWeight = FontWeight.Bold)
                Text("No bank-account setup, withdrawal, transfer or payout action is connected. No payout is simulated.", color = FixItMuted)
            }
            ProviderDemoNotice()
        }
    }
}

@Composable
private fun ProviderReviews() {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            Text("Customer feedback", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text("Sample reviews for interface demonstration.", color = FixItMuted)
            MetricCard("Sample rating", "4.7 / 5", Modifier.fillMaxWidth())
        }
        items(ProviderDemoData.reviews) { review ->
            FixItCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(review.customer, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                    FixItStatusBadge("${review.rating}/5")
                }
                Text(review.job, color = FixItMuted)
                Text(review.comment)
                Text(review.date, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
        item { ProviderDemoNotice() }
    }
}

@Composable
private fun ProviderSettings(notifications: Boolean, emailUpdates: Boolean, onNotifications: (Boolean) -> Unit, onEmail: (Boolean) -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item {
            FixItCard {
                Text("Preferences", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                PreferenceSwitch("Push notifications", "Demo preference only", notifications, onNotifications)
                Divider()
                PreferenceSwitch("Email updates", "Demo preference only", emailUpdates, onEmail)
            }
            FixItCard {
                Text("Account and security", fontWeight = FontWeight.Bold)
                Text("Password changes, account deletion and verification updates require a confirmed backend workflow.", color = FixItMuted)
                FixItSecondaryButton("Account security unavailable", {}, Modifier.fillMaxWidth(), enabled = false)
            }
            ProviderDemoNotice()
        }
    }
}

@Composable
private fun PreferenceSwitch(title: String, subtitle: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(subtitle, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
        }
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

@Composable
private fun SectionTitle(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = FixItInk)
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = FixItMuted)
    }
}

@Composable
private fun ProviderDemoNotice() {
    Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = MaterialTheme.shapes.small) {
        Text(
            "DEMO MODE · Sample records and edits stay in this app session. Quotes are not sent, customer acceptance is not simulated, and no backend or payout service is connected.",
            Modifier.padding(12.dp), style = MaterialTheme.typography.bodySmall
        )
    }
}
