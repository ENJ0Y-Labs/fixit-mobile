# FixIt Mobile

Native Android application for the FixIt marketplace, built with Kotlin and Jetpack Compose. The product specification is ENJ0Y-Labs/fixit-frontend. This is a native implementation, not a WebView wrapper.

## Current status

- Phase 1: Android project foundation and role-separated workspace navigation.
- Phase 2: Shared native design system and reusable Compose components.
- Phase 3: Native customer marketplace and customer workflows, using isolated local demonstration data.
- Phase 4: Native provider dashboard, profile, services, availability, job discovery, quotes, accepted-job progress, earnings, reviews and settings.
- Phase 5: Native admin dashboard, customer/provider directories, verification queue, job oversight, disputes/support, categories, analytics, settings and read-only admin account.

The customer, provider and admin workspaces use local demonstration records. Provider messaging is excluded because the source frontend's root engineering guide explicitly excludes in-app chat and inbox workflows for both customers and providers.

## Stack

- Kotlin and Jetpack Compose
- Material 3 and AndroidX
- Coil for image previews
- JUnit and Compose UI tests

## Run

Open the repository in Android Studio, allow Gradle sync, then run the app configuration on an emulator or Android device. Requirements: JDK 17 and Android SDK 36.

## Provider demo limitations

- Job opportunities, accepted jobs, bids, reviews and earnings are sample records, not live platform data.
- Profile, service, availability, preference, quote and job-progress changes remain in local Compose state for the current app session.
- Saving a quote does not send it to a customer. A quote is not an accepted job.
- Accepted-job status changes are local demonstrations. The customer has not been notified and no real job state has changed.
- Earnings are illustrative and separated into estimated, agreed and paid categories. No payout, withdrawal or bank-account action is available.
- Provider messaging is excluded by the source product rules.
- No provider API endpoint is called. The provider repository documents proposed contracts only, and no backend is deployed.

## Customer demo limitations

- Provider listings and seeded jobs are sample data, not live marketplace records.
- New jobs, cancellation, profile edits and reviews remain in local Compose state for the current app session.
- Payment options are informational only. No card, bank transfer, escrow, or payment-provider operation is performed.
- Image selection previews a local content URI only. No image is uploaded.
- No customer API endpoint is called. The source repository's backend contracts are proposed and no backend is deployed.

## Admin demo limitations

- Admin records, metrics, verification decisions, account-status changes, job-status changes, support-case status changes, category edits and settings are local session demonstrations only.
- The admin entry screen uses fixed demo credentials (`admin@fixit.local` / `FixitDemo123!`) solely to preview the interface. This is not production authentication and is not a security boundary.
- No admin API endpoint is called. The frontend admin routes are proposed contracts, not confirmed live endpoints.
- Verification document names are sample metadata; no private documents are attached. Support case controls do not send messages, assign real cases, resolve real disputes or issue refunds.
- Admin account information is read-only. Password changes, MFA, session management, role changes and audit history require an approved backend authentication contract.
- Backend authorization must protect every admin operation. Hiding screens or requiring a local demo password does not secure admin data.

## Backend discipline

Do not invent endpoints. Keep demo state distinct from remote data. Backend owns authentication, authorization, persistence, payment processing and business rules. Use the approved API contract when the backend is available.
