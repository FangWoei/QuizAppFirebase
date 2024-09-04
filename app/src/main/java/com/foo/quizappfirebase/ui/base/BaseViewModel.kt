package com.foo.quizappfirebase.ui.base

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.data.model.Role
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel: ViewModel() {

    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error

    protected val _finish = MutableSharedFlow<String>()
    val finish: SharedFlow<String> = _finish

    protected val _success = MutableSharedFlow<Role>()
    val success: MutableSharedFlow<Role> = _success

    protected val _successful: MutableSharedFlow<String> = MutableSharedFlow()
    val successful: SharedFlow<String> = _successful

    protected val _quiz = MutableSharedFlow<Quiz>()
    val quiz: SharedFlow<Quiz> = _quiz



    suspend fun <T>errorHandler(func: suspend () -> T?): T? {
        return try {
            func()
        }catch (e: Exception) {
            _error.emit(e.message.toString())
            e.printStackTrace()
            null
        }
    }




}