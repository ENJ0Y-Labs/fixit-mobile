# FixIt Mobile Engineering Guide

The product specification lives in ENJ0Y-Labs/fixit-frontend. Read its root AGENTS.md and the relevant app README before implementing each workflow.

Use Kotlin, Jetpack Compose, Material 3, AndroidX, JUnit and Compose UI tests. Keep Customer, Provider and Admin workflows separate. Shared design tokens and infrastructure may be reused.

Match the frontend's clean, minimal, professional, compact, card-based visual language with light backgrounds, near-black primary actions, subtle borders, rounded corners and clear typography. Adapt desktop layouts to native mobile patterns instead of shrinking them.

The frontend repository says its apps are prototypes and proposed API routes are not confirmed live endpoints. Never invent endpoints, authentication semantics, payment behavior or admin permissions. Backend owns authorization, persistence, payments and business rules. Clearly distinguish demo data from remote data.

Handle loading, empty, success, error, unauthorized and expired-session states for remote screens. Use accessible labels and sensible touch targets. Add tests for meaningful behavior. Never claim a build or test passed unless it was actually run.
