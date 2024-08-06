package ir.sinanakhaei.retrofit.interceptors.httprequest

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

internal class HttpRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Log.d("network-request", request.toString())
        return chain.proceed(request)
    }
}