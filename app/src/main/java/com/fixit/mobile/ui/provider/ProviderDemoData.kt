package com.fixit.mobile.ui.provider

data class ProviderService(
    val id: Int,
    val name: String,
    val description: String,
    val price: String,
    val pricing: String = "Starting from"
)

data class ProviderJob(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val location: String,
    val budget: Int,
    val urgency: String,
    val duration: String,
    val posted: String,
    val responses: Int,
    val status: String = "OPEN",
    val progressNote: String = ""
)

data class ProviderBid(
    val id: String,
    val jobId: String,
    val amount: Int,
    val message: String,
    val schedule: String,
    val status: String = "DEMO SUBMITTED"
)

data class ProviderReview(
    val customer: String,
    val job: String,
    val rating: Int,
    val comment: String,
    val date: String
)

object ProviderDemoData {
    val services = listOf(
        ProviderService(1, "Plumbing", "Tap repairs, leak detection, and basic pipe maintenance.", "15000")
    )

    val opportunities = listOf(
        ProviderJob("job-201", "Repair leaking kitchen sink", "Plumbing", "The kitchen sink pipe is leaking beneath the cabinet. Inspect the joint, replace worn parts if needed, and test for leaks.", "Rumuola, Port Harcourt", 18000, "Within 2 days", "1–2 hours", "Today", 2),
        ProviderJob("job-202", "Install ceiling fans in two rooms", "Electrical", "Install two ceiling fans in a residential flat. Existing wiring is available; confirm it is safe before installation.", "GRA Phase 2, Port Harcourt", 35000, "This week", "2–3 hours", "Yesterday", 4),
        ProviderJob("job-203", "Service split air conditioner", "AC Repair", "Clean filters and coils, inspect drainage, and diagnose why the unit is cooling slowly.", "Peter Odili Road, Port Harcourt", 28000, "Within 3 days", "1–2 hours", "Today", 1),
        ProviderJob("job-204", "Paint a medium-sized bedroom", "Painting", "Prepare and repaint one bedroom. The customer can provide paint after quantity and finish are agreed.", "Rumuokoro, Port Harcourt", 65000, "Flexible", "1 day", "2 days ago", 3),
        ProviderJob("job-205", "Fix a bedroom door lock", "Carpentry", "The door lock sticks and sometimes fails to latch. Inspect the lock and door alignment.", "Mile 3, Port Harcourt", 12000, "Within 2 days", "1 hour", "Today", 0),
        ProviderJob("job-206", "Deep clean a two-bedroom flat", "Cleaning", "Deep clean floors, kitchen surfaces, bathrooms, and windows.", "Old GRA, Port Harcourt", 40000, "This week", "4–5 hours", "Yesterday", 5),
        ProviderJob("job-207", "Replace damaged bathroom tiles", "Tiling", "Replace a small section of damaged bathroom wall tiles and check for moisture.", "Elelenwo, Port Harcourt", 50000, "Flexible", "Half day", "3 days ago", 2),
        ProviderJob("job-208", "Inspect a faulty water pump", "Plumbing", "The water pump starts intermittently. Diagnose the likely fault and provide a quote.", "Rukpokwu, Port Harcourt", 22000, "Within 3 days", "1–2 hours", "Today", 1)
    )

    val acceptedJobs = listOf(
        ProviderJob("job-190", "Replace a bathroom tap", "Plumbing", "Replace the leaking bathroom tap and check the water connection.", "Ada George, Port Harcourt", 14000, "Scheduled", "1 hour", "Accepted yesterday", 3, "ACCEPTED"),
        ProviderJob("job-191", "Repair faulty wall socket", "Electrical", "Inspect and repair one faulty wall socket. Confirm power is isolated before work.", "Rumuibekwe, Port Harcourt", 22000, "Today", "1–2 hours", "Accepted 2 days ago", 2, "IN_PROGRESS", "Initial inspection completed.")
    )

    val reviews = listOf(
        ProviderReview("Customer A", "Kitchen pipe repair", 5, "Arrived on time and explained the repair clearly.", "12 Nov 2026"),
        ProviderReview("Customer B", "Bathroom tap replacement", 4, "Good work and professional communication.", "8 Nov 2026"),
        ProviderReview("Customer C", "Water pump inspection", 5, "Careful inspection and useful recommendations.", "2 Nov 2026")
    )

    val categories = listOf("Plumbing", "Electrical repairs", "Cleaning", "Carpentry", "Painting", "Air conditioning", "Appliance repair", "General maintenance")
    val weekdays = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
}
