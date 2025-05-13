package com.stefware.myapplication.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.interceptor.AuthTokenInterceptor
import com.stefware.myapplication.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(authTokenInterceptor: AuthTokenInterceptor): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authTokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8090/api/v1/") // URL para el emulador de Android
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideManageWiseApiService(retrofit: Retrofit): ManageWiseApiService {
        return retrofit.create(ManageWiseApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUserStoryRepository(apiService: ManageWiseApiService): UserStoryRepository {
        return UserStoryRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideSprintRepository(apiService: ManageWiseApiService): SprintRepository {
        return SprintRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideIssueRepository(apiService: ManageWiseApiService): IssueRepository {
        return IssueRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideStatisticsRepository(apiService: ManageWiseApiService): StatisticsRepository {
        return StatisticsRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideMeetingRepository(apiService: ManageWiseApiService): MeetingRepository {
        return MeetingRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(apiService: ManageWiseApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Provides
    @Singleton
    fun provideIssuesRepository(issueRepository: IssueRepository): com.stefware.myapplication.ui.issues.IssueRepository {
        return com.stefware.myapplication.ui.issues.IssueRepository(issueRepository)
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(application: Application): SharedPreferences {
        return application.getSharedPreferences("manage_wise_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideAuthTokenInterceptor(sharedPreferences: SharedPreferences): AuthTokenInterceptor {
        return AuthTokenInterceptor(sharedPreferences)
    }
}