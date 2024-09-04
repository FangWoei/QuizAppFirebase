package com.foo.quizappfirebase.ui.dashboard.manage

import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.Constants.DELETE
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.data.repo.UserRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ManageViewModel @Inject constructor(
    private val quizRepo: QuizRepo,
    private val userRepo: UserRepo,
    private val resourceProvider: ResourceProvider
): BaseViewModel() {
    fun getAllQuiz(): Flow<List<Quiz>> = quizRepo.getAllQuiz()

    fun deleteQuiz(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                quizRepo.deleteQuiz(id)
            }?.let {
                _finish.emit(
                    resourceProvider.getFormattedString(R.string.success_message, DELETE)
                )
            }
        }
    }
}