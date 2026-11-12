package com.wipro.interview.wiprocleanarchitecture.data

import com.wipro.interview.wiprocleanarchitecture.data.remote.ApiService
import com.wipro.interview.wiprocleanarchitecture.data.repository.UserRepositoryImpl
import com.wipro.interview.wiprocleanarchitecture.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttp(): OkHttpClient {
        return OkHttpClient.Builder().retryOnConnectionFailure(true).addInterceptor(HttpLoggingInterceptor().apply {
            level =
                HttpLoggingInterceptor.Level.BODY
        }).build()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).client(okHttpClient)
            .build().create(ApiService::class.java)
    }

    @Provides
    fun provideUserRepo(apiService: ApiService): UserRepository {
        return UserRepositoryImpl(apiService)
    }

}