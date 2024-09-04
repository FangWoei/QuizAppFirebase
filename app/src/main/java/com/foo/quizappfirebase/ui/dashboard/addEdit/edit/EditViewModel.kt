package com.foo.quizappfirebase.ui.dashboard.addEdit.edit

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.Constants.EDIT
import com.foo.quizappfirebase.core.services.ProcessCSV
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.ui.dashboard.addEdit.base.BaseAddEditViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo,
    private val resourceProvider: ResourceProvider,
    processCSV: ProcessCSV
) : BaseAddEditViewModel(processCSV) {

    private val quizId = state.get<String>("quizId")

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quizId?.let {
                errorHandler {
                    val fetchedQuiz = repo.getQuizById(it)
                    if (fetchedQuiz != null) {
                        questions = fetchedQuiz.questions
                        _quiz.emit(fetchedQuiz)
                    }
                }
            }
        }
    }

    override fun submit(title: String, desc: String, timeLimit: String, selectedCsvFile: Uri?) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotEmpty() && desc.isNotEmpty() && timeLimit.isNotEmpty()) {
                val quiz = repo.getQuizById(quizId!!)
                quiz?.let {
                    errorHandler {
                        repo.updateQuiz(
                            it.copy(
                                title = title,
                                desc = desc,
                                timeLimit = timeLimit,
                                questions = questions
                            )
                        )
                        _successful.emit(
                            resourceProvider.getFormattedString(
                                R.string.success_message, EDIT
                            )
                        )
                    }
                }
            } else _error.emit("Details cannot be empty and TimeLimit must be greater than 0")
        }
    }
}