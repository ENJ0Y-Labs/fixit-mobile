# Phase 6: Mobile backend integration

The Android app has a Retrofit/OkHttp client mapped to the proposed FixIt v1 endpoint catalogue in the frontend repository README.

## API base URL

The frontend contract documents http://localhost:3000/api/v1 for local development. Android emulators reach the host machine through 10.0.2.2, so the default is http://10.0.2.2:3000/api/v1/.

Override this with a Gradle property or environment variable named FIXIT_API_BASE_URL. For example, put FIXIT_API_BASE_URL=https://your-api-host.example/api/v1/ in your user-level Gradle properties. Do not commit private values. Use HTTPS for deployed environments.

## Implemented

- Retrofit service declarations for shared authentication, public marketplace, customer, provider, admin, and optional upload routes.
- OkHttp session cookie handling for server-issued cookies.
- Connection, read, write, and total call timeouts.
- Coroutine-friendly API results with normalized network and HTTP errors.
- Separate authentication, customer, provider, and admin repositories.
- Flexible JSON payloads while the backend schema remains undocumented.
- Cursor/limit support for customer job pagination and query maps for list filters.
- No fake-success fallback when the network is unavailable.

## Contract gaps

These routes remain proposals, not a deployed OpenAPI contract. Backend owners must confirm exact payload fields, response envelopes, status enums, pagination, money units, upload field names/limits, cookie expiry, CSRF protection, and admin authorization.

The frontend engineering guide excludes customer/provider messaging and inbox UI. Messaging has therefore not been added to the mobile app even though message routes appear in the proposed catalogue. That product rule must be explicitly changed before implementation.

## Current limitation

The repositories are ready for ViewModels and screens to consume, but existing Compose screens still use local demo fixtures. This commit establishes the transport and repository foundation; it does not claim that screens are fully switched to a backend that does not yet exist. Requests to the default local URL fail clearly if no API is running.

## Verification

Run ./gradlew testDebugUnitTest and ./gradlew assembleDebug. Then verify authentication, customer jobs/bids/payments/reviews, provider profile/services/availability/bids/progress/earnings, admin operations, pagination, and uploads against a service implementing the proposed contract.
