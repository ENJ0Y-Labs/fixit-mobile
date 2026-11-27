# FixIt Mobile

Native Android application for the FixIt marketplace, built with Kotlin and Jetpack Compose. The product specification is ENJ0Y-Labs/fixit-frontend. This is a native implementation, not a WebView wrapper.

## Current status

- Phase 1: Android project foundation and role-separated workspace navigation.
- Phase 2: Shared native design system and reusable Compose components.
- Phase 3: Native customer marketplace and customer workflows, using isolated local demonstration data.
- Phase 4: Native provider dashboard, profile, services, availability, job discovery, quotes, accepted-job progress, earnings, reviews and settings.
- Phase 5: Native admin dashboard, customer/provider directories, verification queue, job oversight, disputes/support, categories, analytics, settings and read-only admin account.
- Phase 6 foundation: Retrofit/OkHttp transport, route declarations, auth/customer/provider/admin repositories, cookie handling, timeout configuration, and normalized API errors. See [BACKEND_INTEGRATION.md](./BACKEND_INTEGRATION.md).

The customer, provider and admin workspaces still use local demonstration records. The Phase 6 repositories are available, but the Compose screens have not yet been migrated to remote data. Provider messaging is excluded because the source frontend's root engineering guide explicitly excludes in-app chat and inbox workflows for both customers and providers.

## Stack

- Kotlin and Jetpack Compose
- Material 3 and AndroidX
- Coil for image previews
- JUnit and Compose UI tests

## Build and run the app

### Requirements

- Android Studio (current stable release recommended).
- JDK 17. Android Studio's bundled JBR 17 can be selected in **Settings → Build, Execution, Deployment → Build Tools → Gradle → Gradle JDK**.
- Android SDK 36 and the Android SDK Build-Tools installed through **Tools → SDK Manager**.
- An Android emulator (created in **Tools → Device Manager**) or a physical Android device with USB debugging enabled.

### Open the project in Android Studio

1. Clone or download this repository.
2. In Android Studio, select **File → Open** and choose the repository root, the folder containing `settings.gradle.kts`.
3. Wait for Gradle sync to finish. Install any SDK components Android Studio prompts for.
4. Select an emulator from the device dropdown, or connect an Android phone with USB debugging enabled.
5. Select the **app** run configuration and click **Run ▶**. Android Studio will build, install and launch FixIt on the selected device.

If Android Studio does not show an `app` run configuration, use **Run → Edit Configurations → + → Android App**, select the `app` module, and apply.

### Build from the terminal

The repository currently does not include the Gradle Wrapper, so the terminal commands below require Gradle to be installed and available on your PATH, plus JDK 17.

From the repository root:

```bash
gradle :app:assembleDebug
```

The generated debug APK is:

```text
app/build/outputs/apk/debug/fixit.apk
```

To run the unit tests:

```bash
gradle :app:testDebugUnitTest
```

To build and test in one go:

```bash
gradle :app:testDebugUnitTest :app:assembleDebug
```

To install the debug APK on a connected device or running emulator (with Android Debug Bridge available):

```bash
adb install -r app/build/outputs/apk/debug/fixit.apk
```

Then open **FixIt** from the device's app launcher. Alternatively, use Android Studio's Run button to build, install and launch it in one step.

On Windows PowerShell, run the same Gradle tasks using `gradle :app:assembleDebug` and `gradle :app:testDebugUnitTest`.


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

## Backend integration boundary

The routes in the frontend repository are proposed contracts, not a deployed API or OpenAPI schema. The mobile transport and repositories map to those proposed routes, but existing Compose screens remain on demo fixtures until each screen is migrated and verified against a running service. The default emulator URL is a local development target, not a deployed backend. Cookie expiry, CSRF behavior, request/response fields, money units, pagination, upload rules, and admin permissions still require backend confirmation.

The frontend engineering guide excludes customer/provider messaging and inboxes, so those workflows remain excluded from mobile.
