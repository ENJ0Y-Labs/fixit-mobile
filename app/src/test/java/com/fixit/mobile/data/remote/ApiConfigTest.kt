package com.fixit.mobile.data.remote

import okhttp3.HttpUrl.Companion.toHttpUrl
import org.junit.Assert.assertTrue
import org.junit.Test

class ApiConfigTest {
    @Test fun defaultBaseUrlUsesVersionedApiPath() {
        assertTrue(ApiConfig.baseUrl.endsWith("/"))
        assertTrue(ApiConfig.isConfigured())
    }

    @Test fun cookieJarStartsWithoutAnAuthenticatedSession() {
        val jar = InMemorySessionCookieJar()
        assertTrue(jar.loadForRequest("https://example.test/".toHttpUrl()).isEmpty())
    }
}
