package com.foo.quizappfirebase.ui.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentDashboardBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import com.foo.quizappfirebase.ui.loginRegister.LoginRegisterViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    override val viewModel: DashboardViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_dashboard

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

}