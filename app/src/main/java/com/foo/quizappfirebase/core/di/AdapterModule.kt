package com.foo.quizappfirebase.core.di

import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.ui.adapter.QuizAdapter
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
    fun provideNoteAdapter(
        resourceProvider: ResourceProvider
    ): QuizAdapter = QuizAdapter(resourceProvider, emptyList())

}