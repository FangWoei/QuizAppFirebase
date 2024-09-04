package com.foo.quizappfirebase.ui.dashboard

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentDashboardBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardFragment : BaseFragment<FragmentDashboardBinding>() {
    override val viewModel: DashboardViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_dashboard

    override fun onBindView(view: View) {
        super.onBindView(view)
       binding?.btnAddQuiz?.setOnClickListener{
           findNavController().navigate(DashboardFragmentDirections.actionDashboardFragmentToManageFragment())
       }
    }

}