package com.foo.quizappfirebase.ui.studentHome

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentStudentHomeBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import com.foo.quizappfirebase.ui.dashboard.DashboardFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentStudentHomeBinding>() {
    override val viewModel: StudentHomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_student_home
    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.btnAddQuiz?.setOnClickListener{
            findNavController().navigate(StudentHomeFragmentDirections.actionStudentHomeFragmentToAddHomeFragment())
        }
    }



}