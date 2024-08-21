package com.foo.quizappfirebase.ui.loginRegister

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.foo.quizappfirebase.core.Constants.EMAIL_ERR
import com.foo.quizappfirebase.core.Constants.EMAIL_REG
import com.foo.quizappfirebase.core.Constants.NAME_ERR
import com.foo.quizappfirebase.core.Constants.NAME_REG
import com.foo.quizappfirebase.core.Constants.PASS_ERR
import com.foo.quizappfirebase.core.Constants.PASS_REG
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.core.utils.ValidationUtils.validate
import com.foo.quizappfirebase.data.model.Role
import com.foo.quizappfirebase.data.model.User
import com.foo.quizappfirebase.data.model.ValidationField
import com.foo.quizappfirebase.data.repo.UserRepo
import com.foo.quizappfirebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class LoginRegisterViewModel @Inject constructor(
    private val authService: AuthService,
    private val userRepo: UserRepo
): BaseViewModel() {

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                validate(
                    ValidationField(email, EMAIL_REG, EMAIL_ERR),
                    ValidationField(password, PASS_REG, PASS_ERR)
                )

                authService.loginEmailWithPassword(email, password)

                val userRole = userRepo.getUser()
                userRole?.let {
                    _success.emit(it.role)
                }?: throw Exception ("User data not found")

            }?.let {
                _finish.emit("Login successful")
            }
        }
    }

    fun register(
        username: String,
        email: String,
        password: String,
        conPassword: String,
        role: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            errorHandler {
                val error = validate(
                    ValidationField(username, NAME_REG, NAME_ERR),
                    ValidationField(email, EMAIL_REG, EMAIL_ERR),
                    ValidationField(password, PASS_REG, PASS_ERR),
                    ValidationField(conPassword, PASS_REG, PASS_ERR)
                )
                if(error != null) throw Exception(error)
                val userCreated = authService.createUserWithEmailAndPassword(email, password)
                val userRole = Role.valueOf(role)
                userRepo.createUser(User(username, email, userRole))
            }?.let {
                _finish.emit("Registration successful")
            }
        }
    }
}