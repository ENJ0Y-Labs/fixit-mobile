# FixIt Mobile

Native Android application for the FixIt marketplace, built with Kotlin and Jetpack Compose. The product specification is ENJ0Y-Labs/fixit-frontend. This is a native implementation, not a WebView wrapper.

## Current status

- Phase 1: Android project foundation and role-separated workspace navigation.
- Phase 2: Shared native design system and reusable Compose components.
- Phase 3: Native customer marketplace and customer workflows, using isolated local demonstration data.

The customer workspace includes home/discovery, provider search and filters, public provider profiles, job posting, My Jobs, job details and local cancellation, payment information, reviews, and account settings. Customer messaging is intentionally not included: the source frontend's engineering guide explicitly excludes in-app chat and inbox workflows from the current product scope.

## Stack

- Kotlin and Jetpack Compose
- Material 3 and AndroidX
- Coil for image previews
- JUnit and Compose UI tests

## Run

Open the repository in Android Studio, allow Gradle sync, then run the app configuration on an emulator or Android device. Requirements: JDK 17 and Android SDK 36.

## Shared design system

Native design tokens are ported from the shared CSS design system in the frontend repository.

Reusable Compose components include primary and secondary buttons, text fields, dropdowns, cards, status badges, confirmation dialogs, loading/empty/error/offline states, top app bar, bottom navigation, image selection/preview, and a responsive content container.

## Customer demo limitations

- Provider listings and seeded jobs are sample data, not live marketplace records.
- New jobs, cancellation, profile edits and reviews remain in local Compose state for the current app session.
- Payment options are informational only. No card, bank transfer, escrow, or payment-provider operation is performed.
- Image selection previews a local content URI only. No image is uploaded.
- No customer API endpoint is called. The source repository's backend contracts are proposed and no backend is deployed.
- Customer messaging is excluded by the source frontend's current product rules.

Do not present local demo interactions as persisted or completed server operations.

## Other workspaces

Provider and Admin currently retain the Phase 1 workspace scaffold. Their native workflows are not yet converted.

## Backend discipline

Do not invent endpoints. Keep demo state distinct from remote data. Backend owns authentication, authorization, persistence, payment processing and business rules. Use the approved API contract when the backend is available.
