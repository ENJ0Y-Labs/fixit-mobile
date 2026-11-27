# FixIt Mobile

Native Android application for the FixIt marketplace, built with Kotlin and Jetpack Compose. The product specification is ENJ0Y-Labs/fixit-frontend. This is a native implementation, not a WebView wrapper.

## Current status

- Phase 1: Android project foundation and role-separated workspace navigation.
- Phase 2: Shared native design system and reusable Compose components.

The current workspace content remains a navigation scaffold. The design system is reusable infrastructure, not a claim that all customer, provider or admin screens have been converted. Demo copy is not live backend data.

The frontend repository explicitly describes its apps as prototypes and API routes as proposed contracts. Do not claim that an action is persisted or authenticated until an approved backend contract is implemented.

## Stack

- Kotlin and Jetpack Compose
- Material 3 and AndroidX
- Coil for image previews
- JUnit and Compose UI tests

## Run

Open the repository in Android Studio, allow Gradle sync, then run the app configuration on an emulator or Android device. Requirements: JDK 17 and Android SDK 36.

## Shared design system

Native design tokens are ported from the shared CSS design system in the frontend repository.

Reusable Compose components include:

- Primary and secondary buttons
- Text fields and dropdowns
- Cards and status badges
- Confirmation dialogs
- Loading, empty, error and offline states
- Top app bar and bottom navigation
- Image selection and preview
- Responsive content container

Image selection only returns a content URI to the caller. It does not upload a file. Actual upload behavior must follow the backend's approved storage/API contract.

## Workspaces

Customer: discovery, jobs, job posting, payments, reviews and account.
Provider: dashboard, profile, services, job discovery, bids/jobs, earnings, reviews and settings.
Admin: dashboard, customers, providers, verification, jobs, disputes/support, categories, analytics and settings.

## Backend discipline

Do not invent endpoints. Keep demo state distinct from remote data. Backend owns authentication, authorization, persistence, payment processing and business rules. Use the approved API contract when the backend is available.
