package com.foo.quizappfirebase.core.di

import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.ui.adapter.QuestionAdapter
import com.foo.quizappfirebase.ui.adapter.QuizAdapter
import com.foo.quizappfirebase.ui.adapter.StudentAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideQuizAdapter(
        resourceProvider: ResourceProvider
    ): QuizAdapter = QuizAdapter(resourceProvider, emptyList())

    @Provides
    @Singleton
    fun provideQuestionAdapter(
        resourceProvider: ResourceProvider
    ): QuestionAdapter = QuestionAdapter(emptyList())

    @Provides
    @Singleton
    fun provideStudentAdapter(
        resourceProvider: ResourceProvider
    ): StudentAdapter = StudentAdapter(emptyList())
}