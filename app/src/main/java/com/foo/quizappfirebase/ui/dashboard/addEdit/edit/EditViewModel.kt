package com.foo.quizappfirebase.ui.dashboard.addEdit.edit

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.Constants
import com.foo.quizappfirebase.core.Constants.EDIT
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.process.CsvProcessor
import com.foo.quizappfirebase.data.repo.QuizRepo
import com.foo.quizappfirebase.ui.dashboard.addEdit.base.BaseAddEditViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    state: SavedStateHandle,
    private val repo: QuizRepo,
    private val resourceProvider: ResourceProvider,
): BaseAddEditViewModel(){

    private val quizId = state.get<String>("quizId")
    init {
        viewModelScope.launch(Dispatchers.IO) {
            quizId?.let {
                errorHandler { _quiz.value = repo.getQuizById(it) }

            }
        }
    }

    override fun submit(
        title: String,
        desc: String,
        quizIdForSearch: String,
        timeLimit: Int,
        csvFile: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (title.isNotEmpty() && desc.isNotEmpty() && quizIdForSearch.isNotEmpty() && timeLimit > 0) {
                quiz.value?.let {
                    errorHandler {
                        repo.updateQuiz(
                            it.copy(
                                title = title,
                                desc = desc,
                                quizIdForSearch = quizIdForSearch,
                                timeLimit = timeLimit
                            )
                        )
                        _successful.emit(
                            resourceProvider.getFormattedString(
                                R.string.success_message, EDIT
                            )
                        )
                    }
                }
            } else _error.emit("Details cannot be empty! and TimeLimit cannot less than 0")
        }
    }
}