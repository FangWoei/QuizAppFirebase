package com.foo.quizappfirebase.ui.studentHome.add

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.services.AuthService
import com.foo.quizappfirebase.databinding.FragmentAddHomeBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import com.foo.quizappfirebase.ui.studentHome.add.AddHomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddHomeFragment : BaseFragment<FragmentAddHomeBinding>() {
    @Inject
    lateinit var authService: AuthService

    override val viewModel: AddHomeViewModel by viewModels()

    override fun getLayoutResource(): Int = R.layout.fragment_add_home

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            btnSubmit.setOnClickListener {
                findNavController().navigate(
                    AddHomeFragmentDirections.actionAddHomeFragmentToStudentQuizFragment(
                        etQuizID.text.toString()
                    )
                )
            }
        }
    }

}