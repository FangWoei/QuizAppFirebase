package com.foo.quizappfirebase.ui.dashboard.addEdit.add

import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.Constants.ADD
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.process.CsvProcessor
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
    private val csvProcessor: CsvProcessor
): BaseAddEditViewModel() {
    override fun submit(
        title: String,
        desc: String,
        quizIdForSearch: String,
        timeLimit: Int,
        csvFile: Uri?,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if(title.isNotEmpty() && desc.isNotEmpty() && quizIdForSearch.isNotEmpty() && timeLimit > 0) {
                errorHandler {
                    repo.createQuiz(
                        Quiz(
                            title = title,
                            desc = desc,
                            quizIdForSearch = quizIdForSearch,
                            timeLimit = timeLimit,
                            questions = csvProcessor.processQuizCsv(csvFile!!)
                        )
                    )
                }?.let {
                    _successful.emit(
                        resourceProvider.getFormattedString(R.string.success_message, ADD)
                    )
                }
            } else _error.emit("Details cannot be empty! and TimeLimit cannot less than 0")

        }
    }
}