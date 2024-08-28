package com.foo.quizappfirebase.core.di

import android.content.Context
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.process.CsvProcessor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @ApplicationContext context: Context
    ): AuthService = AuthService(context)

    @Provides
    @Singleton
    fun provideResourceProvider(
        @ApplicationContext context: Context
    ): ResourceProvider = ResourceProvider(context)

    @Provides
    @Singleton
    fun provideCSVProcessor(
        @ApplicationContext context: Context
    ): CsvProcessor = CsvProcessor(context)
}