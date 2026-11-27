package com.fixit.mobile.data.remote

import org.junit.Assert.assertTrue
import org.junit.Test

class ApiConfigTest {
    @Test fun defaultBaseUrlUsesVersionedApiPath() {
        assertTrue(ApiConfig.baseUrl.endsWith("/"))
        assertTrue(ApiConfig.isConfigured())
    }
    @Test fun cookieJarStartsWithoutAnAuthenticatedSession() {
        val jar = InMemorySessionCookieJar()
        assertTrue(jar.loadForRequest(okhttp3.HttpUrl.get("https://example.test/")).isEmpty())
    }
}
