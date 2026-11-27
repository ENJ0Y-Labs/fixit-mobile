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

## Testing

Add JUnit tests for demo fixture integrity and state rules. Add Compose UI tests for provider navigation, quote validation, and the accepted-job progress flow. Run gradle testDebugUnitTest and gradle assembleDebug. Instrumentation tests require an emulator/device runner.
