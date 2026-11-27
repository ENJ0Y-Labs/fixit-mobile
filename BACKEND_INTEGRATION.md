# Phase 6: Mobile backend integration

The Android app now has a Retrofit/OkHttp client mapped to the proposed FixIt v1 endpoint catalogue in [fixit-frontend](https://github.com/ENJ0Y-Labs/fixit-frontend/blob/main/README.md).

## API base URL

The frontend contract documents `http://localhost:3000/api/v1` for local development. Android emulators reach the host machine through `10.0.2.2`, so the default is:

```
http://10.0.2.2:3000/api/v1/
```

Override this with a Gradle property or environment variable named `FIXIT_API_BASE_URL`. For example, put this in your user-level `~/.gradle/gradle.properties`:

```properties
FIXIT_API_BASE_URL=https://your-api-host.example/api/v1/
```

Do not commit private values. Use HTTPS for deployed environments.

## Implemented

- Retrofit service declarations for shared authentication, public marketplace, customer, provider, admin, and optional upload routes.
- OkHttp cookie handling for server-issued session cookies.
- Connection, read, write, and total call timeouts.
- Coroutine-friendly API results with normalized network and HTTP errors.
- Separate authentication, customer, provider, and admin repositories.
- Flexible JSON payloads while the backend schema remains undocumented.
- Cursor/limit support for customer job pagination and query maps for list filters.
- No fake-success fallback when the network is unavailable.

## Contract gaps

These routes are still proposals, not a deployed OpenAPI contract. Backend owners must confirm exact payload fields, response envelopes, status enums, pagination, money units, upload field names/limits, cookie expiry, CSRF protection, and admin authorization.

The frontend engineering guide excludes customer/provider messaging and inbox UI. Messaging has therefore not been added to the mobile app despite proposed message routes appearing in the catalogue. That product rule must be explicitly changed before implementation.

## Important current limitation

The repositories are ready for ViewModels and screens to consume, but the existing Compose screens still use their local demo fixtures. This commit establishes the transport and repository foundation; it does not claim that the app's screens are fully switched to a backend that does not yet exist. A request to the default local URL will fail clearly if no API is running.

## Verification

Run:

```bash
./gradlew testDebugUnitTest
./gradlew assembleDebug
```

Then verify against a service implementing the proposed contract: authentication, public discovery, customer jobs/bids/payments/reviews, provider profile/services/availability/bids/progress/earnings, admin operations, pagination, and supported uploads.
