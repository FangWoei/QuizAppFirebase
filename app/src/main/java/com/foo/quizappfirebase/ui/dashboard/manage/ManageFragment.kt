package com.foo.quizappfirebase.ui.dashboard.manage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.core.utils.ValidationUtils.setVisibility
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.databinding.FragmentManageBinding
import com.foo.quizappfirebase.ui.adapter.QuizAdapter
import com.foo.quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ManageFragment : BaseFragment<FragmentManageBinding>() {

    override val viewModel: ManageViewModel by viewModels()
    override fun getLayoutResource(): Int = R.layout.fragment_manage
    @Inject
    lateinit var adapter: QuizAdapter

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapter()
        binding?.fabAdd?.setOnClickListener {
            findNavController().navigate(ManageFragmentDirections.actionManageFragmentToAddFragment())
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.getAllQuiz().collect {
                adapter.setQuestions(it)
                binding?.run {
                    rvQuiz.setVisibility(it.isEmpty())
                    llEmpty.setVisibility(it.isNotEmpty())
                }
            }
        }
    }


    private fun setupAdapter() {
        binding?.rvQuiz?.adapter = adapter
        binding?.rvQuiz?.layoutManager = LinearLayoutManager(requireContext())
        adapter.listener = object: QuizAdapter.Listener {
            override fun onClickItem(quiz: Quiz) {
                findNavController().navigate(
                  ManageFragmentDirections.actionManageFragmentToQuizFragment(
                        quiz.id!!
                    )
                )
            }

            override fun onClickEditItem(quiz: Quiz) {
                findNavController().navigate(
                    ManageFragmentDirections.actionManageFragmentToEditFragment(
                        quiz.id!!
                    )
                )
            }
            override fun onDeleteItem(quiz: Quiz) { viewModel.deleteQuiz(quiz.id!!) }
        }

    }
}