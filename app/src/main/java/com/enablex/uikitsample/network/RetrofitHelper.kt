package com.enablex.uikitsample.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitHelper {
    //
    private const val baseUrl:String = "https://demo.enablex.io/"
    private const val  kAppId:String = "AppId";
    private const val  kAppkey:String = "AppKey";


    fun getClient(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
    }
    private fun getHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient.Builder()
            .readTimeout(120, TimeUnit.SECONDS)
            .connectTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(Interceptor { chain: Interceptor.Chain ->
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")
                        .addHeader("x-app-id", kAppId)
                        .addHeader("x-app-key", kAppkey)

                        .build() //
                chain.proceed(request)
            }).build()
    }
}
