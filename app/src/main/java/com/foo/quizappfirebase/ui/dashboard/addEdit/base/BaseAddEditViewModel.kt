package com.foo.quizappfirebase.ui.dashboard.addEdit.base

import android.net.Uri
import com.foo.quizappfirebase.core.services.ProcessCSV
import com.foo.quizappfirebase.data.model.Question
import com.foo.quizappfirebase.ui.base.BaseViewModel

abstract class BaseAddEditViewModel(
    private val processCSV: ProcessCSV
) : BaseViewModel() {
    protected var questions: List<Question> = emptyList()

    abstract fun submit(
        title: String,
        desc: String,
        timeLimit: String,
        selectedCsvFile: Uri?
    )

    fun getQuestionsFromCSV(uri: Uri) {
        questions = processCSV.getQuestionsFromCSV(uri)
    }
}