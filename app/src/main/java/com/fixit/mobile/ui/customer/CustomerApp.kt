package com.fixit.mobile.ui.customer

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fixit.mobile.ui.components.FixItBottomNavigation
import com.fixit.mobile.ui.components.FixItCard
import com.fixit.mobile.ui.components.FixItDropdown
import com.fixit.mobile.ui.components.FixItImagePicker
import com.fixit.mobile.ui.components.FixItNavigationItem
import com.fixit.mobile.ui.components.FixItPrimaryButton
import com.fixit.mobile.ui.components.FixItResponsiveContent
import com.fixit.mobile.ui.components.FixItSecondaryButton
import com.fixit.mobile.ui.components.FixItStatusBadge
import com.fixit.mobile.ui.components.FixItTextField
import com.fixit.mobile.ui.components.FixItTopAppBar
import com.fixit.mobile.ui.model.Workspace
import com.fixit.mobile.ui.theme.FixItInk
import com.fixit.mobile.ui.theme.FixItMuted
import com.fixit.mobile.ui.theme.FixItPrimary
import com.fixit.mobile.ui.theme.FixItSurface

private val mainTabs = listOf("Home", "Search", "My Jobs", "Account")

@Composable
fun CustomerApp(onWorkspaceChange: (Workspace) -> Unit) {
    val jobs = remember { mutableStateListOf<CustomerJob>().apply { addAll(CustomerDemoData.jobs) } }
    var page by remember { mutableStateOf("Home") }
    var query by remember { mutableStateOf("") }
    var categoryFilter by remember { mutableStateOf("All categories") }
    var ratingFilter by remember { mutableStateOf(false) }
    var availableOnly by remember { mutableStateOf(false) }
    var selectedProvider by remember { mutableStateOf<CustomerProvider?>(null) }
    var selectedJobId by remember { mutableStateOf<String?>(null) }
    var showCancelDialog by remember { mutableStateOf(false) }
    var showWorkspaceMenu by remember { mutableStateOf(false) }
    var notice by remember { mutableStateOf("") }

    val selectedJob = jobs.firstOrNull { it.id == selectedJobId }
    val isMainPage = page in mainTabs
    val pageTitle = when (page) {
        "Provider profile" -> "Provider profile"
        "Post a Job" -> "Post a job"
        "Job details" -> "Job details"
        "Payment" -> "Payment"
        "Review" -> "Write a review"
        else -> page
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            FixItTopAppBar(
                title = "FixIt",
                subtitle = if (page == "Home" || page == "Search") "CUSTOMER MARKETPLACE · DEMO" else pageTitle.uppercase(),
                navigationIcon = {
                    if (!isMainPage) IconButton(onClick = {
                        page = when (page) {
                            "Provider profile" -> "Search"
                            "Post a Job", "Job details" -> "My Jobs"
                            "Payment", "Review" -> "Job details"
                            else -> "Home"
                        }
                    }) { Icon(Icons.Default.ArrowBack, contentDescription = "Go back") }
                },
                actions = {
                    BoxMenu(showWorkspaceMenu, { showWorkspaceMenu = it }, onWorkspaceChange)
                }
            )
        },
        bottomBar = {
            if (isMainPage) {
                FixItBottomNavigation(
                    items = listOf(
                        FixItNavigationItem("Home", Icons.Default.Home),
                        FixItNavigationItem("Search", Icons.Default.Search),
                        FixItNavigationItem("My Jobs", Icons.Default.Work),
                        FixItNavigationItem("Account", Icons.Default.Person)
                    ),
                    selectedLabel = page,
                    onSelect = { page = it }
                )
            }
        }
    ) { insets ->
        FixItResponsiveContent(
            modifier = Modifier.fillMaxSize().padding(insets),
            maxContentWidth = 720.dp
        ) {
            when (page) {
                "Home" -> HomeScreen(
                    jobs = jobs,
                    onCategory = { categoryFilter = it; page = "Search" },
                    onSearch = { page = "Search" },
                    onProvider = { selectedProvider = it; page = "Provider profile" },
                    onPostJob = { page = "Post a Job" },
                    onJob = { selectedJobId = it; page = "Job details" }
                )
                "Search" -> SearchScreen(
                    query = query,
                    onQuery = { query = it },
                    category = categoryFilter,
                    onCategory = { categoryFilter = it },
                    ratingOnly = ratingFilter,
                    onRatingOnly = { ratingFilter = it },
                    availableOnly = availableOnly,
                    onAvailableOnly = { availableOnly = it },
                    onProvider = { selectedProvider = it; page = "Provider profile" }
                )
                "Provider profile" -> selectedProvider?.let { provider ->
                    ProviderProfileScreen(provider, onRequest = { categoryFilter = provider.category; page = "Post a Job" })
                } ?: EmptySelection("Provider profile unavailable", "Return to search and select a provider.")
                "My Jobs" -> MyJobsScreen(jobs, onPostJob = { page = "Post a Job" }, onJob = { selectedJobId = it; page = "Job details" })
                "Post a Job" -> PostJobScreen(
                    initialCategory = categoryFilter.takeIf { it != "All categories" } ?: "",
                    onSubmit = { draft ->
                        val newJob = CustomerJob(
                            id = "DEMO-${(jobs.size + 1043).toString()}",
                            title = draft.title.trim(),
                            category = draft.category,
                            description = draft.description.trim(),
                            status = "AWAITING_BIDS",
                            location = draft.location.trim(),
                            budget = draft.budget.toIntOrNull() ?: 0
                        )
                        jobs.add(0, newJob)
                        selectedJobId = newJob.id
                        notice = "Job request saved in this demo session. It has not been sent to a server."
                        page = "My Jobs"
                    }
                )
                "Job details" -> selectedJob?.let { job ->
                    JobDetailsScreen(
                        job = job,
                        onCancel = { showCancelDialog = true },
                        onPayment = { page = "Payment" },
                        onReview = { page = "Review" }
                    )
                } ?: EmptySelection("No job selected", "Choose a job from My Jobs to see its details.")
                "Payment" -> selectedJob?.let { PaymentScreen(it) }
                    ?: EmptySelection("Payment unavailable", "Select a job before opening its payment summary.")
                "Review" -> selectedJob?.let { ReviewScreen(it, onSubmitted = { notice = "Your review was saved locally for this demo."; page = "My Jobs" }) }
                    ?: EmptySelection("Review unavailable", "Select a completed job before writing a review.")
                "Account" -> AccountScreen(notice = notice, onNotice = { notice = it })
                else -> HomeScreen(jobs, { categoryFilter = it; page = "Search" }, { page = "Search" }, { selectedProvider = it; page = "Provider profile" }, { page = "Post a Job" }, { selectedJobId = it; page = "Job details" })
            }
        }
    }

    if (showCancelDialog && selectedJob != null) {
        AlertDialog(
            onDismissRequest = { showCancelDialog = false },
            title = { Text("Cancel this job?") },
            text = { Text("This changes the status in the local demo only. No cancellation request will be sent to a server.") },
            confirmButton = {
                TextButton(onClick = {
                    val index = jobs.indexOfFirst { it.id == selectedJob.id }
                    if (index >= 0) jobs[index] = jobs[index].copy(status = "CANCELLED", canCancel = false)
                    showCancelDialog = false
                    page = "My Jobs"
                    notice = "Demo job cancelled locally. No server was contacted."
                }) { Text("Cancel job", color = Color(0xFFB42318)) }
            },
            dismissButton = { TextButton(onClick = { showCancelDialog = false }) { Text("Keep job") } }
        )
    }
}

@Composable
private fun BoxMenu(expanded: Boolean, onExpanded: (Boolean) -> Unit, onWorkspaceChange: (Workspace) -> Unit) {
    androidx.compose.foundation.layout.Box {
        IconButton(onClick = { onExpanded(true) }) { Icon(Icons.Default.AccountCircle, contentDescription = "Switch workspace") }
        androidx.compose.material3.DropdownMenu(expanded = expanded, onDismissRequest = { onExpanded(false) }) {
            Workspace.entries.forEach { workspace ->
                androidx.compose.material3.DropdownMenuItem(
                    text = { Text("${workspace.label} workspace") },
                    onClick = { onExpanded(false); onWorkspaceChange(workspace) }
                )
            }
        }
    }
}

@Composable
private fun HomeScreen(
    jobs: List<CustomerJob>,
    onCategory: (String) -> Unit,
    onSearch: () -> Unit,
    onProvider: (CustomerProvider) -> Unit,
    onPostJob: () -> Unit,
    onJob: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            FixItCard {
                Text("HOME SERVICES, MADE SIMPLE", color = FixItPrimary, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
                Text("Get your home fixed by people you can trust.", style = MaterialTheme.typography.headlineSmall, color = FixItInk, fontWeight = FontWeight.Bold)
                Text("Find local service providers, compare their rates, and choose the right person for the job.", color = FixItMuted)
                FixItPrimaryButton("Find a professional", onSearch, Modifier.fillMaxWidth())
            }
        }
        item {
            SectionHeading("Popular services", "What do you need help with?")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomerDemoData.categories.take(3).forEach { category ->
                    FilterChip(selected = false, onClick = { onCategory(category) }, label = { Text(category) })
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomerDemoData.categories.drop(3).take(2).forEach { category ->
                    FilterChip(selected = false, onClick = { onCategory(category) }, label = { Text(category) })
                }
                FilterChip(selected = false, onClick = { onCategory("All categories") }, label = { Text("More") })
            }
        }
        item {
            SectionHeading("Find the right professional", "Sample provider listings")
            FixItSecondaryButton("Browse and filter providers", onSearch, Modifier.fillMaxWidth())
        }
        items(CustomerDemoData.providers.take(3)) { provider ->
            ProviderCard(provider, onClick = { onProvider(provider) })
        }
        item {
            FixItCard {
                Text("Your jobs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${jobs.count { it.status == "IN_PROGRESS" }} active jobs · ${jobs.count { it.status == "AWAITING_BIDS" }} awaiting bids", color = FixItMuted)
                FixItSecondaryButton("View my jobs", { onJob(jobs.firstOrNull()?.id ?: "") }, Modifier.fillMaxWidth(), enabled = jobs.isNotEmpty())
            }
        }
        item { FixItPrimaryButton("Post a new job", onPostJob, Modifier.fillMaxWidth()) }
        item { DemoNotice() }
    }
}

@Composable
private fun SearchScreen(
    query: String,
    onQuery: (String) -> Unit,
    category: String,
    onCategory: (String) -> Unit,
    ratingOnly: Boolean,
    onRatingOnly: (Boolean) -> Unit,
    availableOnly: Boolean,
    onAvailableOnly: (Boolean) -> Unit,
    onProvider: (CustomerProvider) -> Unit
) {
    val providers = CustomerDemoData.providers.filter { provider ->
        val matchesQuery = query.isBlank() || listOf(provider.name, provider.profession, provider.service, provider.category).any { it.contains(query, true) }
        val matchesCategory = category == "All categories" || provider.category.equals(category, true)
        matchesQuery && matchesCategory && (!ratingOnly || provider.rating >= 4.8) && (!availableOnly || provider.available)
    }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Discover trusted local help", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
            Text("Search, compare and open public provider profiles.", color = FixItMuted)
            Spacer(Modifier.height(8.dp))
            FixItTextField(query, onQuery, "Search services or providers", placeholder = "e.g. plumber, cleaning")
            FixItDropdown("Service category", listOf("All categories") + CustomerDemoData.categories, category, onCategory)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(selected = ratingOnly, onClick = { onRatingOnly(!ratingOnly) }, label = { Text("4.8+ rating") })
                FilterChip(selected = availableOnly, onClick = { onAvailableOnly(!availableOnly) }, label = { Text("Available now") })
            }
            Text("${providers.size} sample listings", color = FixItMuted, style = MaterialTheme.typography.labelMedium)
        }
        if (providers.isEmpty()) {
            item { EmptySelection("No matching providers", "Try another search or clear one of the filters.") }
        } else {
            items(providers) { provider -> ProviderCard(provider, onClick = { onProvider(provider) }) }
        }
        item { DemoNotice() }
    }
}

@Composable
private fun ProviderCard(provider: CustomerProvider, onClick: () -> Unit) {
    FixItCard(Modifier.clickable(onClick = onClick)) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            Icon(Icons.Default.Person, null, tint = FixItPrimary, modifier = Modifier.size(36.dp))
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(provider.name, fontWeight = FontWeight.Bold, color = FixItInk)
                Text(provider.profession, color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                Text(provider.service, color = FixItInk, style = MaterialTheme.typography.bodyMedium)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFE3A008), modifier = Modifier.size(16.dp))
                    Text(" ${provider.rating} (${provider.reviews})  ·  ${provider.location}", style = MaterialTheme.typography.bodySmall, color = FixItMuted)
                }
                Text("₦${provider.hourlyRate.toString().reversed().chunked(3).joinToString(",").reversed()}/hr", color = FixItInk, fontWeight = FontWeight.SemiBold)
            }
            FixItStatusBadge(if (provider.available) "Available" else "Unavailable")
        }
    }
}

@Composable
private fun ProviderProfileScreen(provider: CustomerProvider, onRequest: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        item {
            FixItCard {
                Icon(Icons.Default.Person, null, tint = FixItPrimary, modifier = Modifier.size(52.dp))
                Text(provider.name, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
                Text(provider.profession, color = FixItMuted)
                Text(provider.description, color = FixItInk)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Star, null, tint = Color(0xFFE3A008))
                    Text(" ${provider.rating} · ${provider.reviews} reviews")
                }
                Text("₦${provider.hourlyRate}/hour · ${provider.location}", fontWeight = FontWeight.SemiBold)
                FixItStatusBadge(if (provider.available) "Available" else "Currently unavailable")
                FixItPrimaryButton("Request this service", onRequest, Modifier.fillMaxWidth())
            }
        }
        item {
            SectionHeading("Services", "Public profile information")
            FixItCard { Text(provider.service, fontWeight = FontWeight.SemiBold); Text(provider.description, color = FixItMuted) }
        }
        item { DemoNotice() }
    }
}

private data class JobDraft(
    val title: String,
    val category: String,
    val description: String,
    val urgency: String,
    val date: String,
    val time: String,
    val location: String,
    val budget: String
)

@Composable
private fun PostJobScreen(initialCategory: String, onSubmit: (JobDraft) -> Unit) {
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(initialCategory) }
    var description by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("Standard") }
    var date by remember { mutableStateOf("") }
    var time by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }
    var error by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<Uri?>(null) }

    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Tell providers what needs to be done.", color = FixItMuted)
            FixItTextField(title, { title = it; error = "" }, "Job title", placeholder = "e.g. Fix a leaking kitchen pipe")
            FixItDropdown("Service category", CustomerDemoData.categories, category, { category = it; error = "" })
            FixItTextField(description, { description = it }, "Detailed description", placeholder = "Describe the work required", singleLine = false)
            SectionHeading("Urgency", "Choose how soon you need the service")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf("Standard", "Urgent", "ASAP").forEach { option ->
                    FilterChip(selected = urgency == option, onClick = { urgency = option }, label = { Text(option) })
                }
            }
            FixItTextField(date, { date = it }, "Preferred date (optional)", placeholder = "YYYY-MM-DD")
            FixItTextField(time, { time = it }, "Preferred time (optional)", placeholder = "e.g. 10:00 AM")
            FixItTextField(location, { location = it; error = "" }, "Service location", placeholder = "Area, city")
            FixItTextField(budget, { budget = it.filter(Char::isDigit); error = "" }, "Maximum budget (₦)", keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
            Text("Photos (optional)", fontWeight = FontWeight.SemiBold)
            FixItImagePicker(photoUri, { photoUri = it }, label = "Choose job photo")
            Text("Photo upload is not connected. The selected image is only previewed on this device.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            if (error.isNotBlank()) Text(error, color = Color(0xFFB42318))
            FixItPrimaryButton("Save demo job request", {
                if (title.isBlank() || category.isBlank() || location.isBlank() || budget.toIntOrNull() == null || budget.toInt() <= 0) {
                    error = "Enter a title, category, location and valid budget to continue."
                } else {
                    onSubmit(JobDraft(title, category, description, urgency, date, time, location, budget))
                }
            }, Modifier.fillMaxWidth())
            DemoNotice()
        }
    }
}

@Composable
private fun MyJobsScreen(jobs: List<CustomerJob>, onPostJob: () -> Unit, onJob: (String) -> Unit) {
    var filter by remember { mutableStateOf("All") }
    val filtered = jobs.filter {
        filter == "All" || when (filter) {
            "Active" -> it.status == "IN_PROGRESS"
            "Awaiting bids" -> it.status == "AWAITING_BIDS"
            "Completed" -> it.status == "COMPLETED"
            "Cancelled" -> it.status == "CANCELLED"
            else -> true
        }
    }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text("Your service requests", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold, color = FixItInk)
            Text("Track job progress and review request details.", color = FixItMuted)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                listOf("All", "Active", "Awaiting bids", "Completed", "Cancelled").forEach { option ->
                    FilterChip(selected = filter == option, onClick = { filter = option }, label = { Text(option) })
                }
            }
            FixItPrimaryButton("Post a job", onPostJob, Modifier.fillMaxWidth())
        }
        if (filtered.isEmpty()) item { EmptySelection("No jobs in this view", "Try another status filter or post a new job.") }
        items(filtered, key = { it.id }) { job ->
            FixItCard(Modifier.clickable { onJob(job.id) }) {
                Text(job.title, fontWeight = FontWeight.Bold, color = FixItInk)
                Text("${job.category} · ${job.location}", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                FixItStatusBadge(job.status.replace("_", " "))
                Text("Budget: ₦${job.budget}", fontWeight = FontWeight.SemiBold)
                if (job.providerName != null) Text("Provider: ${job.providerName}", color = FixItMuted)
                Text("View details →", color = FixItPrimary, fontWeight = FontWeight.SemiBold)
            }
        }
        item { DemoNotice() }
    }
}

@Composable
private fun JobDetailsScreen(job: CustomerJob, onCancel: () -> Unit, onPayment: () -> Unit, onReview: () -> Unit) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FixItCard {
                Text(job.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = FixItInk)
                FixItStatusBadge(job.status.replace("_", " "))
                Text(job.description, color = FixItMuted)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, null, tint = FixItMuted)
                    Text(job.location, color = FixItInk)
                }
                Text("Category: ${job.category}")
                Text("Budget: ₦${job.budget}", fontWeight = FontWeight.SemiBold)
            }
        }
        item {
            FixItCard {
                Text("Provider", fontWeight = FontWeight.Bold)
                Text(job.providerName ?: "No provider has been assigned yet.", color = FixItMuted)
                job.providerRating?.let { Text("Rating: $it / 5") }
            }
        }
        item {
            FixItCard {
                Text("Payment summary", fontWeight = FontWeight.Bold)
                Text(job.paymentStatus, color = FixItMuted)
                if (job.status == "IN_PROGRESS") FixItSecondaryButton("View payment information", onPayment, Modifier.fillMaxWidth())
                if (job.status == "COMPLETED") FixItPrimaryButton("Write a review", onReview, Modifier.fillMaxWidth())
            }
        }
        if (job.canCancel && job.status != "CANCELLED" && job.status != "COMPLETED") {
            item { OutlinedButton(onClick = onCancel, modifier = Modifier.fillMaxWidth()) { Text("Cancel job") } }
        }
        item { DemoNotice() }
    }
}

@Composable
private fun PaymentScreen(job: CustomerJob) {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FixItCard {
                Icon(Icons.Default.CheckCircle, null, tint = FixItPrimary, modifier = Modifier.size(34.dp))
                Text("Payment information", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("FixIt's payment interface is a preview. Escrow, card charging and bank transfers are not connected in this build.", color = FixItMuted)
                Text(job.title, fontWeight = FontWeight.SemiBold)
                Text("₦${job.budget}", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = FixItInk)
                FixItStatusBadge(job.paymentStatus)
            }
        }
        item {
            SectionHeading("Available methods", "Illustrative options only")
            listOf("Debit or credit card", "Bank transfer").forEach { method ->
                FixItCard {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Payments, null, tint = FixItPrimary)
                        Column(Modifier.weight(1f)) {
                            Text(method, fontWeight = FontWeight.SemiBold)
                            Text("Unavailable until payment integration is configured", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
            FixItPrimaryButton("Payment unavailable", {}, Modifier.fillMaxWidth(), enabled = false)
            DemoNotice()
        }
    }
}

@Composable
private fun ReviewScreen(job: CustomerJob, onSubmitted: () -> Unit) {
    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }
    var anonymous by remember { mutableStateOf(false) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FixItCard {
                Text("Review your experience", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text(job.providerName ?: "Service provider", color = FixItMuted)
                Text(job.title, fontWeight = FontWeight.SemiBold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    (1..5).forEach { value ->
                        IconButton(onClick = { rating = value }) {
                            Icon(Icons.Default.Star, contentDescription = "$value stars", tint = if (value <= rating) Color(0xFFE3A008) else FixItMuted)
                        }
                    }
                }
                Text(if (rating == 0) "Select a rating" else "$rating out of 5")
                FixItTextField(comment, { comment = it }, "Share more details", placeholder = "How was the service?", singleLine = false)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    androidx.compose.material3.Checkbox(checked = anonymous, onCheckedChange = { anonymous = it })
                    Text("Post anonymously")
                }
                FixItPrimaryButton("Save demo review", onSubmitted, Modifier.fillMaxWidth(), enabled = rating > 0)
                Text("This review is saved only as a local demo interaction. It is not published.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun AccountScreen(notice: String, onNotice: (String) -> Unit) {
    var name by remember { mutableStateOf("Customer Demo") }
    var email by remember { mutableStateOf("demo@fixit.local") }
    var phone by remember { mutableStateOf("+234 800 000 0000") }
    var editing by remember { mutableStateOf(false) }
    var notifications by remember { mutableStateOf(true) }
    var photo by remember { mutableStateOf<Uri?>(null) }
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(18.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            FixItCard {
                Icon(Icons.Default.AccountCircle, null, tint = FixItPrimary, modifier = Modifier.size(52.dp))
                Text("Customer demo profile", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                if (editing) {
                    FixItTextField(name, { name = it }, "Full name")
                    FixItTextField(email, { email = it }, "Email address")
                    FixItTextField(phone, { phone = it }, "Phone number")
                    FixItPrimaryButton("Save profile in this session", { editing = false; onNotice("Profile changes are stored only in this session.") }, Modifier.fillMaxWidth())
                    FixItSecondaryButton("Cancel", { editing = false }, Modifier.fillMaxWidth())
                } else {
                    Text(name, fontWeight = FontWeight.SemiBold)
                    Text(email, color = FixItMuted)
                    Text(phone, color = FixItMuted)
                    FixItSecondaryButton("Edit profile", { editing = true }, Modifier.fillMaxWidth())
                }
            }
        }
        item {
            FixItCard {
                Text("Profile photo", fontWeight = FontWeight.Bold)
                FixItImagePicker(photo, { photo = it }, label = "Choose profile photo")
                Text("Photo selection is a local preview. It is not uploaded.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
        item {
            FixItCard {
                Text("Preferences", fontWeight = FontWeight.Bold)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Notifications")
                        Text("Demo preference only", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
                    }
                    androidx.compose.material3.Switch(checked = notifications, onCheckedChange = { notifications = it })
                }
            }
        }
        item {
            FixItCard {
                Text("Support and trust", fontWeight = FontWeight.Bold)
                Text("For account or service support, use the approved FixIt support channel when it becomes available.", color = FixItMuted)
                Text("In-app messaging is not part of the current customer product scope.", color = FixItMuted, style = MaterialTheme.typography.bodySmall)
            }
        }
        if (notice.isNotBlank()) item { Text(notice, color = FixItPrimary) }
        item { DemoNotice() }
    }
}

@Composable
private fun SectionHeading(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
        Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = FixItInk)
        Text(subtitle, style = MaterialTheme.typography.bodySmall, color = FixItMuted)
    }
}

@Composable
private fun DemoNotice() {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEAF3F0)),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            "DEMO MODE · Sample listings and job changes stay on this device. No backend, payment or upload service is connected.",
            Modifier.padding(12.dp),
            color = Color(0xFF245B49),
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
private fun EmptySelection(title: String, message: String) {
    Column(Modifier.fillMaxWidth().padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(Icons.Default.Build, null, tint = FixItMuted, modifier = Modifier.size(34.dp))
        Text(title, fontWeight = FontWeight.Bold, color = FixItInk)
        Text(message, color = FixItMuted)
    }
}
