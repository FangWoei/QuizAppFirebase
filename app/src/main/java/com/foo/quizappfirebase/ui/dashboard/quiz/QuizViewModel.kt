package com.foo.quizappfirebase.ui.dashboard.quiz

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo
): BaseViewModel() {
    init {
        state.get<String>("quizId")?.let { getQuizById(it) }
    }
    private fun getQuizById(id:String) {
        viewModelScope.launch (Dispatchers.IO){
            errorHandler {
                val quiz = repo.getQuizById(id) ?: throw Exception("Quiz doesn't exist")
                _quiz.emit(quiz)
            }
        }
    }
}