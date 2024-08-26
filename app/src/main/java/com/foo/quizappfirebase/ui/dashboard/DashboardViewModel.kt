package com.foo.quizappfirebase.ui.dashboard

import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.data.repo.UserRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

}