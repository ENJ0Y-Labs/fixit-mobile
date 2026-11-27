# FixIt Mobile Engineering Guide

The product specification lives in ENJ0Y-Labs/fixit-frontend. Read its root AGENTS.md and the relevant app README before implementing each workflow.

Use Kotlin, Jetpack Compose, Material 3, AndroidX, JUnit and Compose UI tests. Keep Customer, Provider and Admin workflows separate. Shared design tokens and infrastructure may be reused.

The shared native design system must follow fixit-frontend/shared/fixit-design-system.css. Current core tokens include background #F8F9FF, surface #FFFFFF, navy text #0B1C30, muted text #526174, border #E2E8F0 and emerald primary #006948. Reuse ui/theme tokens and ui/components before creating one-off styles.

Adapt desktop layouts to native mobile patterns instead of shrinking them. Use sensible touch targets and responsive content constraints.

The frontend repository says its apps are prototypes and proposed API routes are not confirmed live endpoints. Never invent endpoints, authentication semantics, payment behavior or admin permissions. Backend owns authorization, persistence, payments and business rules. Clearly distinguish demo data from remote data.

Handle loading, empty, success, error, unauthorized and expired-session states for remote screens. Use accessible labels and sensible touch targets. Add tests for meaningful behavior. Never claim a build or test passed unless it was actually run.
