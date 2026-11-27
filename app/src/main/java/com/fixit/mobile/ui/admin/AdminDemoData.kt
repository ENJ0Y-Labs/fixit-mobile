package com.fixit.mobile.ui.admin

data class AdminCustomer(
    val id: String, val name: String, val email: String, val phone: String,
    val location: String, val jobs: Int, val account: String
)

data class AdminProvider(
    val id: String, val name: String, val business: String, val category: String,
    val location: String, val rating: Double, val jobs: Int,
    val verification: String, val account: String
)

data class VerificationCase(
    val id: String, val provider: String, val business: String, val service: String,
    val location: String, val status: String, val documents: List<Pair<String, String>>
)

data class AdminJob(
    val id: String, val title: String, val category: String, val customer: String,
    val provider: String, val location: String, val status: String, val budget: Int, val bids: Int
)

data class SupportCase(
    val id: String, val type: String, val subject: String, val customer: String,
    val provider: String, val status: String, val priority: String, val summary: String
)

data class AdminCategory(
    val id: String, val name: String, val description: String,
    val providers: Int, val jobs: Int, val active: Boolean
)

val demoAdminCustomers = listOf(
    AdminCustomer("CUS-2084", "Ada Nwankwo", "ada.nwankwo@example.test", "+234 803 555 2084", "GRA Phase 2, Port Harcourt", 8, "Active"),
    AdminCustomer("CUS-2083", "Chidi Okoro", "chidi.okoro@example.test", "+234 806 555 2083", "Rumuola, Port Harcourt", 4, "Active"),
    AdminCustomer("CUS-2082", "Mariam Abdullahi", "mariam.abdullahi@example.test", "+234 809 555 2082", "Woji, Port Harcourt", 12, "Active"),
    AdminCustomer("CUS-2081", "Tunde Kalu", "tunde.kalu@example.test", "+234 802 555 2081", "Trans Amadi, Port Harcourt", 3, "Active"),
    AdminCustomer("CUS-2080", "Ifeoma Briggs", "ifeoma.briggs@example.test", "+234 805 555 2080", "Old GRA, Port Harcourt", 9, "Active"),
    AdminCustomer("CUS-2079", "Daniel Tamuno", "daniel.tamuno@example.test", "+234 807 555 2079", "Eliozu, Port Harcourt", 2, "Suspended"),
    AdminCustomer("CUS-2078", "Blessing George", "blessing.george@example.test", "+234 810 555 2078", "Ada George, Port Harcourt", 6, "Active"),
    AdminCustomer("CUS-2077", "Emeka Wali", "emeka.wali@example.test", "+234 814 555 2077", "Rumuokoro, Port Harcourt", 1, "Active")
)

val demoAdminProviders = listOf(
    AdminProvider("PRV-1042", "Chinedu Okafor", "CO Plumbing Services", "Plumbing", "Rumuola, Port Harcourt", 4.8, 42, "Verified", "Active"),
    AdminProvider("PRV-1041", "Amaka Eze", "BrightSpark Electricals", "Electrical", "GRA Phase 2, Port Harcourt", 4.9, 36, "Verified", "Active"),
    AdminProvider("PRV-1040", "Ibrahim Musa", "Musa Cooling Works", "AC repair", "Rumuokoro, Port Harcourt", 4.5, 19, "Pending review", "Active"),
    AdminProvider("PRV-1039", "Blessing Wali", "NeatSpace Cleaning", "Cleaning", "Trans Amadi, Port Harcourt", 4.7, 28, "Verified", "Active"),
    AdminProvider("PRV-1038", "Emeka Nwosu", "Nwosu Home Repairs", "Carpentry", "Eliozu, Port Harcourt", 4.2, 11, "Pending review", "Active"),
    AdminProvider("PRV-1037", "Precious George", "PG Painting", "Painting", "Old GRA, Port Harcourt", 4.6, 23, "Verified", "Active"),
    AdminProvider("PRV-1036", "Samuel Briggs", "Briggs Pest Control", "Pest control", "Mile 3, Port Harcourt", 4.1, 8, "Rejected", "Suspended"),
    AdminProvider("PRV-1035", "Favour Ogbonda", "FreshFlow Plumbing", "Plumbing", "Ada George, Port Harcourt", 4.9, 31, "Verified", "Active")
)

val demoVerificationCases = listOf(
    VerificationCase("VER-1048", "Adaeze Nwosu", "Nwosu Plumbing Services", "Plumbing", "GRA Phase 2, Port Harcourt", "Pending review", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Submitted", "Professional certificate" to "Missing")),
    VerificationCase("VER-1047", "Chinedu Okafor", "CO Electrical Works", "Electrical", "Rumuola, Port Harcourt", "Pending review", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Submitted", "Professional certificate" to "Submitted")),
    VerificationCase("VER-1046", "Mariam Danjuma", "FreshSpace Cleaning", "Cleaning", "Woji, Port Harcourt", "Needs information", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Needs replacement", "Professional certificate" to "Not required")),
    VerificationCase("VER-1045", "Tunde Ekanem", "CoolPoint AC Services", "AC repair", "Trans Amadi, Port Harcourt", "Pending review", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Submitted", "Professional certificate" to "Submitted")),
    VerificationCase("VER-1044", "Ifeoma Briggs", "Briggs Home Repairs", "Carpentry", "Old GRA, Port Harcourt", "Approved", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Submitted", "Professional certificate" to "Submitted")),
    VerificationCase("VER-1043", "Daniel Tamuno", "Tamuno Pest Control", "Pest control", "Eliozu, Port Harcourt", "Rejected", listOf("Government-issued ID" to "Submitted", "Proof of address" to "Submitted", "Professional certificate" to "Missing"))
)

val demoAdminJobs = listOf(
    AdminJob("FX-2058", "Kitchen sink pipe repair", "Plumbing", "Ada Nwankwo", "Chinedu Okafor", "GRA Phase 2, Port Harcourt", "IN_PROGRESS", 28000, 3),
    AdminJob("FX-2057", "Electrical safety inspection", "Electrical", "Chidi Opara", "Not assigned", "Rumuola, Port Harcourt", "AWAITING_BIDS", 35000, 2),
    AdminJob("FX-2056", "Two-bedroom deep cleaning", "Cleaning", "Mariam Abdullahi", "Blessing George", "Woji, Port Harcourt", "COMPLETED", 45000, 4),
    AdminJob("FX-2055", "Water heater installation", "Plumbing", "Tunde Ekanem", "Adaeze Nwosu", "Trans Amadi, Port Harcourt", "CANCELLED", 65000, 1),
    AdminJob("FX-2054", "Split AC servicing", "AC repair", "Ifeoma Briggs", "Tunde Ekanem", "Old GRA, Port Harcourt", "IN_PROGRESS", 22000, 2),
    AdminJob("FX-2053", "Wardrobe door adjustment", "Carpentry", "Daniel Tamuno", "Not assigned", "Eliozu, Port Harcourt", "AWAITING_BIDS", 18000, 0),
    AdminJob("FX-2052", "Home fumigation service", "Pest control", "Emeka Wali", "Daniel Tamuno", "Rumuokoro, Port Harcourt", "COMPLETED", 38000, 3),
    AdminJob("FX-2051", "Laundry collection and delivery", "Laundry", "Blessing George", "Mariam Danjuma", "Ada George, Port Harcourt", "IN_PROGRESS", 12000, 2)
)

val demoSupportCases = listOf(
    SupportCase("DSP-1042", "Dispute", "Work marked complete before repair was finished", "Tunde Ekanem", "Adaeze Nwosu", "OPEN", "High", "Customer reports that the water heater installation was not completed."),
    SupportCase("SUP-1041", "Support", "Unable to update phone number", "Ada Nwankwo", "Not assigned", "UNDER_REVIEW", "Normal", "Customer says the profile form does not save a replacement phone number."),
    SupportCase("DSP-1040", "Dispute", "Final amount differs from initial estimate", "Ifeoma Briggs", "Tunde Ekanem", "WAITING_CUSTOMER", "High", "Customer questions an additional charge mentioned after the service began."),
    SupportCase("SUP-1039", "Support", "Cannot see provider replies", "Chidi Opara", "Not assigned", "OPEN", "Normal", "Customer reports that replies to a job request are not appearing."),
    SupportCase("DSP-1038", "Dispute", "Provider did not arrive at agreed time", "Daniel Tamuno", "Not assigned", "UNDER_REVIEW", "Urgent", "Customer says the scheduled visit was missed."),
    SupportCase("SUP-1037", "Support", "Question about account verification", "Not provided", "Blessing George", "WAITING_PROVIDER", "Low", "Provider wants clarification about a submitted verification document."),
    SupportCase("DSP-1036", "Dispute", "Reported damage after cleaning service", "Mariam Abdullahi", "Blessing George", "RESOLVED", "High", "Customer reported a damaged item after a cleaning visit.")
)

val demoAdminCategories = listOf(
    AdminCategory("CAT-001", "Plumbing", "Water supply, pipes, taps, drainage, and bathroom plumbing services.", 42, 126, true),
    AdminCategory("CAT-002", "Electrical", "Electrical installation, repairs, inspection, and maintenance.", 36, 104, true),
    AdminCategory("CAT-003", "Cleaning", "Residential and commercial cleaning services.", 58, 182, true),
    AdminCategory("CAT-004", "AC repair", "Air-conditioning installation, servicing, and repairs.", 24, 76, true),
    AdminCategory("CAT-005", "Carpentry", "Woodwork, furniture repairs, fittings, and custom installations.", 19, 63, true),
    AdminCategory("CAT-006", "Laundry", "Laundry collection, washing, ironing, and delivery.", 21, 91, true),
    AdminCategory("CAT-007", "Pest control", "Residential pest inspection and treatment services.", 13, 38, true),
    AdminCategory("CAT-008", "Glasswork", "Glass repairs, replacement, and aluminium window services.", 11, 29, true),
    AdminCategory("CAT-009", "Painting", "Interior and exterior painting and surface preparation.", 9, 17, false),
    AdminCategory("CAT-010", "Moving services", "Household and office moving, packing, and furniture handling.", 5, 8, false)
)

fun filterAdminRecords(query: String, records: List<String>): List<String> =
    if (query.isBlank()) records else records.filter { it.contains(query.trim(), ignoreCase = true) }

fun formatAdminNaira(amount: Int): String = "₦" + "%,d".format(amount)
