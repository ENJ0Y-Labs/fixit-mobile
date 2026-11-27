# FixIt Mobile Engineering Guide

The product specification lives in ENJ0Y-Labs/fixit-frontend. Read its root AGENTS.md and the relevant app README before implementing each workflow.

Use Kotlin, Jetpack Compose, Material 3, AndroidX, JUnit and Compose UI tests. Keep Customer, Provider and Admin workflows separate. Shared design tokens and infrastructure may be reused.

The shared native design system follows fixit-frontend/shared/fixit-design-system.css. Reuse ui/theme tokens and ui/components before creating one-off styles. Adapt desktop layouts to native mobile patterns instead of shrinking them.

The frontend repository says its apps are prototypes and proposed API routes are not confirmed live endpoints. Never invent endpoints, authentication semantics, payment behavior or admin permissions. Backend owns authorization, persistence, payments and business rules. Clearly distinguish demo data from remote data.

Handle loading, empty, success, error, unauthorized and expired-session states for remote screens. Use accessible labels and sensible touch targets. Add tests for meaningful behavior. Never claim a build or test passed unless it was actually run.

## Customer conversion rules

The customer app is implemented in ui/customer/. Keep customer state and navigation separate from Provider and Admin workspaces. Reuse shared components.

Customer records and mutations are local demo state. Do not imply persistence. Payment, escrow, uploads and review publishing are not connected. Do not invent endpoints.

## Provider conversion rules

The provider app is implemented in ui/provider/ProviderApp.kt with fixtures in ProviderDemoData.kt. Keep provider state and navigation separate from Customer and Admin. Reuse shared Compose components.

The source root AGENTS.md explicitly excludes in-app chat, inboxes, conversation views and message composers for both customer and provider apps. Do not add messaging without an explicit product decision that updates the source rule.

Provider opportunities, accepted jobs, bids, profile, services, availability, earnings, reviews and settings are local demo data. A locally saved quote is not sent to a customer; it is not an accepted job. Do not simulate customer acceptance, payout, withdrawal, server persistence or notifications. Accepted-job progress changes are session-only demonstrations.

No provider API endpoint is called. The source provider API documentation is proposed, not a confirmed backend contract. Do not add API calls until backend approval and a dedicated integration task.

## Versioning rules (Pidgin)

Anytime you finish fix one issue/bug, complete one phase, or prepare any PR wey go enter `main`, you MUST update the app version for that change. No merge without version bump.

Use this format:
- **Bug fix or small correction:** increase the PATCH number. Example: `0.5.0` → `0.5.1`.
- **New feature or completed phase:** increase the MINOR number and reset PATCH to zero. Example: `0.5.1` → `0.6.0`.
- **Breaking change wey fit affect existing users or compatibility:** increase the MAJOR number and reset the other two numbers. Example: `0.6.0` → `1.0.0`.

If one PR contain different kinds of work, use the highest version change wey apply. No increase version twice for the same work just because the issue dey inside a PR. Every separate PR wey enter `main` must carry the correct version bump inside that PR. PR wey no change app code or user-facing app behaviour still needs PATCH bump under this project rule.

Whenever `versionName` change, increase `versionCode` too. The `versionCode` must always be a higher whole number than the previous release; no reuse am and no reduce am. Keep the versioned APK filename and GitHub Actions artifact name in sync with the new `versionName` (for example, `fixit-mobile-v0.5.1.apk`). Check say the CI workflow still runs `gradle :app:testDebugUnitTest :app:assembleDebug` and uploads the correctly versioned APK.

Before you merge any PR, confirm say `app/build.gradle.kts` get the new `versionName` and higher `versionCode`, and say the APK workflow dey use the same version. No claim say build or tests pass unless CI or local run actually confirm am.

## Testing

Add JUnit tests for demo fixture integrity and state rules. Add Compose UI tests for provider navigation, quote validation, and the accepted-job progress flow. Run gradle testDebugUnitTest and gradle assembleDebug. Instrumentation tests require an emulator/device runner.
