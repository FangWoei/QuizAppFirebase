package com.foo.quizappfirebase.ui.studentHome

import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.repo.UserRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentHomeViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {
    fun logout() {
        authService.logOut()
    }
}