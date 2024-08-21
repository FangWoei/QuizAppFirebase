package com.foo.quizappfirebase.ui.loginRegister

import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.data.model.Role
import com.foo.quizappfirebase.data.model.User
import com.foo.quizappfirebase.databinding.FragmentLoginRegisterBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginRegisterFragment : BaseFragment<FragmentLoginRegisterBinding>() {
    override val viewModel: LoginRegisterViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_login_register

    private var isRegisterMode = false


    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            btnLogin.setOnClickListener {
                val selectedRole = etRole.selectedItem.toString()
                if (isRegisterMode) {
                    viewModel.register(
                        etUsername.text.toString(),
                        etEmail.text.toString(),
                        etPassword.text.toString(),
                        etConPassword.text.toString(),
                        selectedRole
                    )
                    val role = Role.valueOf(selectedRole)
                    if (role == Role.TEACHER) {
                        findNavController().navigate(
                            LoginRegisterFragmentDirections.actionLoginRegisterFragmentToDashboardFragment()
                        )
                    } else {
                        findNavController().navigate(
                            LoginRegisterFragmentDirections.actionLoginRegisterFragmentToStudentHomeFragment()
                        )
                    }
                } else {
                    viewModel.login(
                        etEmail.text.toString(),
                        etPassword.text.toString()
                    )
                    lifecycleScope.launch {

                    viewModel.success.collect {role ->
                        when (role) {
                            Role.TEACHER ->  findNavController().navigate(
                                LoginRegisterFragmentDirections.actionLoginRegisterFragmentToDashboardFragment()
                            )

                            Role.STUDENT -> findNavController().navigate(
                                LoginRegisterFragmentDirections.actionLoginRegisterFragmentToStudentHomeFragment()
                            )
                        }
                    }

                    }


                }

            }

            btnRegisterOrLogin.setOnClickListener {
                toggleMode()
            }
        }
    }
    private fun toggleMode() {
        if (isRegisterMode) {
            // Switch to Login Mode
            binding?.etTitle?.text = getString(R.string.login)
            binding?.btnRegisterOrLogin?.text = getString(R.string.register)
            binding?.etUsername?.visibility = View.GONE
            binding?.etConPassword?.visibility = View.GONE
            binding?.etRole?.visibility = View.GONE
        } else {
            // Switch to Register Mode
            binding?.etTitle?.text = getString(R.string.register)
            binding?.btnRegisterOrLogin?.text = getString(R.string.login)
            binding?.etUsername?.visibility = View.VISIBLE
            binding?.etConPassword?.visibility = View.VISIBLE
            binding?.etRole?.visibility = View.VISIBLE
        }
        isRegisterMode = !isRegisterMode
    }

}
