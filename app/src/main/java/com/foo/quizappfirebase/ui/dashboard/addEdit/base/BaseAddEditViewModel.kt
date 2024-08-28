package com.foo.quizappfirebase.ui.dashboard.addEdit.base

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.foo.quizappfirebase.ui.base.BaseViewModel

abstract class BaseAddEditViewModel() : BaseViewModel() {

    abstract fun submit(
        title: String,
        desc: String,
        quizIdForSearch: String,
        timeLimit: Int,
        csvFile: Uri?
    )
}