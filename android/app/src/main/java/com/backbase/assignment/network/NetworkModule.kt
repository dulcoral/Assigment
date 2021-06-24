package com.backbase.assignment.network

import com.backbase.assignment.BuildConfig
import com.backbase.assignment.repository.MovieApiService
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    private fun createOkHttpBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder().apply {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = BuildConfig.HTTP_LOGGING_INTERCEPTOR
            })
            addInterceptor(createApiKeyInterceptor())
            writeTimeout(10, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            connectTimeout(10, TimeUnit.SECONDS)
        }
    }

    private fun createApiKeyInterceptor(): Interceptor {
        return Interceptor {
            val initRequest = it.request()
            val httpUrl = initRequest.url()

            val url = httpUrl.newBuilder()
                .addQueryParameter("api_key", BuildConfig.MOVIE_API_KEY)
                .build()

            val request = initRequest.newBuilder().url(url).build()
            it.proceed(request)
        }
    }

    private fun createRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return retrofitBuilder(
            okHttpClient,
            BuildConfig.MOVIE_API_BASE_URL,
            GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create(),
            RxJava2CallAdapterFactory.create()
        ).build()
    }

    private fun retrofitBuilder(
        httpClient: OkHttpClient,
        path: String,
        gson: Gson,
        factory: CallAdapter.Factory
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .callFactory { httpClient.newCall(it) }
            .baseUrl(path)
            .addConverterFactory(
                GsonConverterFactory.create(gson)
            )
            .addCallAdapterFactory(factory)
    }

    @Provides
    @Reusable
    fun provideMoviesApiOkHttpClient(): OkHttpClient {
        return createOkHttpBuilder().build()
    }

    @Provides
    @Reusable
    fun provideMoviesApiRetrofit(
        httpClient: OkHttpClient
    ): Retrofit {
        return createRetrofit(
            httpClient
        )
    }

    @Provides
    @Reusable
    fun provideMoviesApiService(retrofit: Retrofit): MovieApiService {
        return retrofit.create(MovieApiService::class.java)
    }

}