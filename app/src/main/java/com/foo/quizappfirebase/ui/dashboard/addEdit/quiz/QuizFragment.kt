package com.foo.quizappfirebase.ui.dashboard.addEdit.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentQuizBinding
import com.foo.quizappfirebase.ui.adapter.QuestionAdapter
import com.foo.quizappfirebase.ui.base.BaseFragment
import com.foo.quizappfirebase.ui.dashboard.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>() {
    override val viewModel: QuizViewModel by viewModels()
    private lateinit var questionAdapter: QuestionAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_quiz

    override fun onBindData(view: View) {
        super.onBindData(view)

        questionAdapter = QuestionAdapter(emptyList())
        binding?.rvQuestion?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter
        }

        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                if (quiz != null) {
                    binding?.run {
                        etTitle.text = quiz.title
                        etDesc.text = quiz.desc
                        etQuizForSearch.text = quiz.quizIdForSearch
                        etTimeLimit.text = quiz.timeLimit.toString()
                    }
                    quiz.questions.let { questions ->
                        questionAdapter.setQuestion(questions)
                    }
                }
            }
        }
    }

}