package com.foo.quizappfirebase.core.di

import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {
    @Provides
    @Singleton
    fun provideQuizRepo(authService: AuthService): QuizRepo {
        return QuizRepo(authService)
    }

    @Provides
    @Singleton
    fun provideUserRepo(authService: AuthService): UserRepo {
        return UserRepo(authService)
    }
}