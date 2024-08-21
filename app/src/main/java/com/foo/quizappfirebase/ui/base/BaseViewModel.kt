package com.foo.quizappfirebase.ui.base

import androidx.lifecycle.ViewModel
import com.foo.quizappfirebase.data.model.Role
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

abstract class BaseViewModel: ViewModel() {
    protected val _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> = _error
    protected val _finish = MutableSharedFlow<String>()
    val finish: SharedFlow<String> = _finish
    protected val _success = MutableSharedFlow<Role>()
    val success: MutableSharedFlow<Role> = _success


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