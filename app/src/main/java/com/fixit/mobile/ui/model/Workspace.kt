package com.fixit.mobile.ui.model

enum class Workspace(val label: String) { CUSTOMER("Customer"), PROVIDER("Provider"), ADMIN("Admin") }

data class WorkspaceSection(val title: String, val description: String, val summary: String, val items: List<String>)

object WorkspaceCatalog {
    fun sections(workspace: Workspace): List<WorkspaceSection> = when (workspace) {
        Workspace.CUSTOMER -> listOf(
            WorkspaceSection("Home", "Find trusted professionals for your home.", "Discover services and providers near you.", listOf("Browse service categories", "Search and filter providers", "Explore provider profiles")),
            WorkspaceSection("My jobs", "Keep track of the work you have requested.", "Your job activity will appear here.", listOf("Awaiting bids", "In progress", "Completed jobs")),
            WorkspaceSection("Post a job", "Tell providers what needs to be done.", "Create a clear job request for professionals to review.", listOf("Choose a service", "Describe the work", "Add location and preferred schedule")),
            WorkspaceSection("Messages", "Keep conversations about your jobs together.", "Conversations appear here when messaging is connected.", listOf("Customer and provider conversations")),
            WorkspaceSection("Payments", "Review payment information for your jobs.", "Payment actions require an available payment backend.", listOf("Payment history", "Payment status")),
            WorkspaceSection("Reviews", "Share feedback after a job is completed.", "Your submitted reviews will appear here.", listOf("Rate completed work")),
            WorkspaceSection("Account", "Manage your customer profile.", "Profile settings and preferences.", listOf("Personal details", "Contact information", "Preferences")))
        Workspace.PROVIDER -> listOf(
            WorkspaceSection("Dashboard", "Your service business at a glance.", "Track opportunities, active work and earnings.", listOf("New job opportunities", "Active jobs", "Earnings overview")),
            WorkspaceSection("Profile", "Manage how customers see your business.", "Keep your professional information up to date.", listOf("Business details", "Profile information", "Verification status")),
            WorkspaceSection("Services", "Choose the services you provide.", "Manage service categories and availability.", listOf("Service categories", "Service descriptions", "Availability")),
            WorkspaceSection("Find jobs", "Discover requests from customers.", "Available jobs appear when connected to the backend.", listOf("Browse available jobs", "Review job details")),
            WorkspaceSection("My jobs", "Manage jobs you have responded to or accepted.", "Track bids and accepted work.", listOf("Submitted bids", "Accepted jobs", "Job progress")),
            WorkspaceSection("Messages", "Communicate with your customers.", "Conversations appear here when messaging is connected.", listOf("Customer conversations")),
            WorkspaceSection("Earnings", "Review your payment and earnings information.", "Payout data requires the approved backend contract.", listOf("Earnings summary", "Payment history")),
            WorkspaceSection("Reviews", "See feedback from customers.", "Customer reviews will appear here.", listOf("Ratings and feedback")),
            WorkspaceSection("Settings", "Manage provider account preferences.", "Account and notification preferences.", listOf("Account settings", "Notifications")))
        Workspace.ADMIN -> listOf(
            WorkspaceSection("Dashboard", "Monitor the FixIt marketplace.", "Platform overview and operational activity.", listOf("Marketplace activity", "Jobs overview", "Pending actions")),
            WorkspaceSection("Customers", "Review customer accounts.", "Customer records load from the admin API.", listOf("Customer directory", "Account status")),
            WorkspaceSection("Providers", "Manage service-provider accounts.", "Provider records and account status.", listOf("Provider directory", "Provider details")),
            WorkspaceSection("Verification", "Review provider verification requests.", "Verification cases load from the admin API.", listOf("Pending verification", "Review submitted documents")),
            WorkspaceSection("Jobs", "Oversee marketplace jobs.", "Monitor job records and reported issues.", listOf("Job directory", "Job status")),
            WorkspaceSection("Disputes & support", "Handle customer and provider support cases.", "Support cases load from the admin API.", listOf("Open disputes", "Support requests")),
            WorkspaceSection("Categories", "Manage marketplace service categories.", "Category changes require backend authorization.", listOf("Service categories", "Category status")),
            WorkspaceSection("Analytics", "Review marketplace performance.", "Reports use verified backend data.", listOf("Marketplace metrics", "Operational reports")),
            WorkspaceSection("Settings", "Manage administrative preferences.", "Administrative configuration.", listOf("Workspace preferences", "Account settings")))
    }
}
