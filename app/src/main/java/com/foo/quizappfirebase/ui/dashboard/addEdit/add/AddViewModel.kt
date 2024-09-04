package com.foo.quizappfirebase.ui.dashboard.addEdit.add

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.Constants.ADD
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.core.services.ProcessCSV
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Question
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.ui.dashboard.addEdit.base.BaseAddEditViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val repo: QuizRepo,
    private val resourceProvider: ResourceProvider,
    private val authService: AuthService,
    processCSV: ProcessCSV,
) : BaseAddEditViewModel(processCSV) {

    override fun submit(
        title: String,
        desc: String,
        timeLimit: String,
        selectedCsvFile: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotEmpty() && desc.isNotEmpty() && timeLimit.isNotEmpty()) {
                errorHandler {
                    val id = authService.getUid() ?: throw Exception("User not found")
                    repo.createQuiz(
                        Quiz(
                            teacherId = id,
                            title = title,
                            desc = desc,
                            timeLimit = timeLimit,
                            questions = getCurrentQuestions()
                        )
                    )
                }?.let {
                    _successful.emit(
                        resourceProvider.getFormattedString(R.string.success_message, ADD)
                    )
                }
            } else _error.emit("Details cannot be empty!")
        }
    }

    private fun getCurrentQuestions(): List<Question> {
        return questions
    }
}