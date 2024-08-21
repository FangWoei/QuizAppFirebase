package com.foo.quizappfirebase.core.di

import android.content.Context
import com.foo.quizappfirebase.core.services.AuthService
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
    fun provideAuthService(@ApplicationContext context: Context): AuthService = AuthService(context)
}