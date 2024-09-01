package com.example.scottishpower.di

import android.util.Log
import com.example.scottishpower.BuildConfig
import com.example.scottishpower.data.repositories.api.PlaceholderApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private val TAG = ApiModule::class.simpleName

    @Provides
    @BaseUrl
    fun provideBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        fun(message: String) {
            Log.d(TAG, message)
        },
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    @Singleton
    fun providesClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(@BaseUrl baseUrl: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(client)
            .build()

    @Provides
    @Singleton
    fun providePlaceholderApi(retrofit: Retrofit): PlaceholderApi = retrofit.create(PlaceholderApi::class.java)

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    private annotation class BaseUrl
}