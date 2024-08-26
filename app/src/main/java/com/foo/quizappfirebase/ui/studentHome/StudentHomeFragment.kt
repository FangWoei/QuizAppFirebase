package com.foo.quizappfirebase.ui.studentHome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentStudentHomeBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import com.foo.quizappfirebase.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StudentHomeFragment : BaseFragment<FragmentStudentHomeBinding>() {
    override val viewModel: StudentHomeViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_student_home
    override fun onBindView(view: View) {
        super.onBindView(view)
    }

}