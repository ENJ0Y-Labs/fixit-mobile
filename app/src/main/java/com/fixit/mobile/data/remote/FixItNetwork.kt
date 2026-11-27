package com.fixit.mobile.data.remote

import com.fixit.mobile.BuildConfig
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URI
import java.util.concurrent.TimeUnit

object ApiConfig {
    val baseUrl: String get() = BuildConfig.API_BASE_URL.trim().let { if (it.endsWith("/")) it else "$it/" }
    fun isConfigured(): Boolean = runCatching {
        val parsed = URI(baseUrl)
        (parsed.scheme == "http" || parsed.scheme == "https") &&
            !parsed.host.isNullOrBlank() && parsed.path.orEmpty().contains("/api/v1/")
    }.getOrDefault(false)
}

class InMemorySessionCookieJar : CookieJar {
    private val cookies = mutableMapOf<String, Cookie>()
    @Synchronized override fun saveFromResponse(url: HttpUrl, newCookies: List<Cookie>) {
        newCookies.forEach { cookie ->
            val key = cookie.domain + "|" + cookie.path + "|" + cookie.name
            if (cookie.expiresAt <= System.currentTimeMillis()) cookies.remove(key) else cookies[key] = cookie
        }
    }
    @Synchronized override fun loadForRequest(url: HttpUrl): List<Cookie> {
        cookies.entries.removeAll { it.value.expiresAt <= System.currentTimeMillis() }
        return cookies.values.filter { it.matches(url) }
    }
    @Synchronized fun clear() = cookies.clear()
}

object FixItNetwork {
    val sessionCookieJar = InMemorySessionCookieJar()
    val client: OkHttpClient by lazy {
        OkHttpClient.Builder().cookieJar(sessionCookieJar)
            .connectTimeout(20, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).callTimeout(45, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
    }
    val api: FixItApi by lazy {
        require(ApiConfig.isConfigured()) { "Set FIXIT_API_BASE_URL to the backend /api/v1/ URL." }
        Retrofit.Builder().baseUrl(ApiConfig.baseUrl).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build().create(FixItApi::class.java)
    }
}
