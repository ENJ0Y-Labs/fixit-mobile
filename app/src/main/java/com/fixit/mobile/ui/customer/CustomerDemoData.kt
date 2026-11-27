package com.fixit.mobile.ui.customer

data class CustomerProvider(
    val id: String,
    val name: String,
    val profession: String,
    val category: String,
    val service: String,
    val location: String,
    val rating: Double,
    val reviews: Int,
    val hourlyRate: Int,
    val available: Boolean,
    val description: String
)

data class CustomerJob(
    val id: String,
    val title: String,
    val category: String,
    val description: String,
    val status: String,
    val location: String,
    val budget: Int,
    val providerName: String? = null,
    val providerRating: Double? = null,
    val paymentStatus: String = "Not paid",
    val canCancel: Boolean = true
)

object CustomerDemoData {
    val categories = listOf(
        "Plumbing", "Electrical", "Cleaning", "Carpentry", "Painting",
        "AC repair", "Laundry", "Pest control", "Glasswork", "Moving services"
    )

    val providers = listOf(
        CustomerProvider("emeka-nwachukwu", "Emeka Nwachukwu", "Professional Plumber", "Plumbing", "Kitchen and bathroom plumbing", "Victoria Island", 4.9, 124, 4500, true, "Kitchen and bathroom plumbing, leak detection and pipe repairs."),
        CustomerProvider("chioma-adeyemi", "Chioma Adeyemi", "Professional Cleaner", "Cleaning", "Home and deep cleaning", "Surulere", 4.8, 89, 3000, true, "Reliable home cleaning and deep-cleaning services."),
        CustomerProvider("tunde-bello", "Tunde Bello", "Master Electrician", "Electrical", "Electrical repairs and installations", "Ikeja", 5.0, 55, 6000, true, "Electrical repairs, installations and safety checks."),
        CustomerProvider("blessing-okafor", "Blessing Okafor", "Interior Painter", "Painting", "Interior painting and finishing", "Ikoyi", 4.7, 42, 4000, false, "Interior painting, wall preparation and finishing."),
        CustomerProvider("chinedu-obi", "Chinedu Obi", "Carpenter", "Carpentry", "Furniture and cabinet repairs", "Yaba", 4.6, 31, 5000, true, "Furniture repairs, cabinet installation and carpentry."),
        CustomerProvider("aisha-yusuf", "Aisha Yusuf", "Home Cleaner", "Cleaning", "Move-in and move-out cleaning", "Surulere", 4.5, 67, 3500, true, "Move-in, move-out and apartment cleaning."),
        CustomerProvider("samuel-eze", "Samuel Eze", "Electrician", "Electrical", "Socket and wiring repairs", "Lekki Phase 1", 4.4, 24, 4200, true, "Socket, lighting and residential wiring repairs."),
        CustomerProvider("ifeoma-nnaji", "Ifeoma Nnaji", "Plumber", "Plumbing", "Bathroom pipe repairs", "Victoria Island", 4.3, 19, 3800, false, "Bathroom pipe repairs and plumbing maintenance.")
    )

    val jobs = listOf(
        CustomerJob("DEMO-1042", "Repair leaking kitchen sink", "Plumbing", "The kitchen sink is leaking beneath the cabinet. Inspect the pipe connection and replace worn fittings.", "IN_PROGRESS", "Rumuola, Port Harcourt", 28500, "Chidi Okafor", 4.8, "Escrow funded", false),
        CustomerJob("DEMO-1041", "Install ceiling fan in bedroom", "Electrical", "Install one ceiling fan and check the existing wiring and wall switch.", "AWAITING_BIDS", "Mile 3, Port Harcourt", 18000),
        CustomerJob("DEMO-1038", "Deep clean a two-bedroom apartment", "Cleaning", "Deep cleaning of bedrooms, kitchen, bathrooms, windows and common areas.", "COMPLETED", "GRA Phase 2, Port Harcourt", 42000, "Amaka Cleaning Services", 4.9, "Paid", false),
        CustomerJob("DEMO-1035", "Paint living room and hallway", "Painting", "Prepare and repaint the living room and hallway. Paint will be supplied by the customer.", "AWAITING_BIDS", "Woji, Port Harcourt", 75000),
        CustomerJob("DEMO-1029", "Fix damaged wardrobe hinge", "Carpentry", "Replace the broken hinge and realign the wardrobe door.", "CANCELLED", "Rumuokoro, Port Harcourt", 8500, canCancel = false)
    )
}
