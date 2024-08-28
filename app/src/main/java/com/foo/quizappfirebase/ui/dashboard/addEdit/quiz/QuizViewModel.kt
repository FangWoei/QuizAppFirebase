package com.foo.quizappfirebase.ui.dashboard.addEdit.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo
): BaseViewModel() {
    private val quizId = state.get<String>("quizId")
    init {
        viewModelScope.launch(Dispatchers.IO) {
            quizId?.let {
                errorHandler { _quiz.value = repo.getQuizById(it) }

            }
        }
    }
}