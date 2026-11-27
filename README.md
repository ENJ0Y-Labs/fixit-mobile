# FixIt Mobile

Native Android application for the FixIt marketplace, built with Kotlin and Jetpack Compose. The product specification is ENJ0Y-Labs/fixit-frontend. This is a native implementation, not a WebView wrapper.

## Current status

Phase 1 establishes the Android application foundation, shared visual tokens, and role-separated navigation catalogues for customer, provider and admin workspaces. The current screens are scaffolding, not the completed conversion. Demo copy is not live backend data.

The frontend repository explicitly describes its apps as prototypes and API routes as proposed contracts. There is no confirmed deployed backend. Do not claim that an action is persisted or authenticated until an approved backend contract is implemented.

## Stack

- Kotlin and Jetpack Compose
- Material 3 and AndroidX
- JUnit

## Run

Open the repository in Android Studio, allow Gradle sync, then run the app configuration on an emulator or Android device. Requirements: JDK 17 and Android SDK 36.

## Workspaces

Customer: discovery, jobs, job posting, messages, payments, reviews and account.
Provider: dashboard, profile, services, job discovery, bids/jobs, messages, earnings, reviews and settings.
Admin: dashboard, customers, providers, verification, jobs, disputes/support, categories, analytics and settings.

## Backend discipline

Do not invent endpoints. Keep demo state distinct from remote data. Backend owns authentication, authorization, persistence, payment processing and business rules. Use the approved API contract when the backend is available.
