package com.foo.quizappfirebase.ui.dashboard.quiz

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.databinding.FragmentQuizBinding
import com.foo.quizappfirebase.ui.adapter.QuestionAdapter
import com.foo.quizappfirebase.ui.adapter.StudentAdapter
import com.foo.quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : BaseFragment<FragmentQuizBinding>() {
    override val viewModel: QuizViewModel by viewModels()
    @Inject
    lateinit var adapter: StudentAdapter
    @Inject
    lateinit var questionAdapter: QuestionAdapter
    override fun getLayoutResource(): Int = R.layout.fragment_quiz
    private lateinit var clipboardManager: ClipboardManager
    private var currentView: String = "Questions"

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupAdapters()
        clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding?.btnSwitch?.setOnClickListener {
            currentView = if(currentView == "Questions") "Students" else "Questions"
            switchView()
        }
    }
    override fun onBindData(view: View) {
        super.onBindData(view)
        clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        binding?.rvQuestion?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = questionAdapter

        }

        lifecycleScope.launch {
            viewModel.quiz.collect {
                questionAdapter.setQuestion(it.questions)
                adapter.setStudents(it.studentList)
                viewQuiz(it)
            }
        }


    }

    private fun viewQuiz(quiz: Quiz){
        binding?.run {
            etTitle.text = quiz.title
            etDesc.text = quiz.desc
            etTimeLimit.text = quiz.timeLimit
            btnCopyID.setOnClickListener {
                copyQuizId(quiz.id!!)
            }
        }
    }

    private fun copyQuizId(id: String) {
        val clipData = ClipData.newPlainText("CLIP_ID", id)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun setupAdapters() {
        questionAdapter = QuestionAdapter(emptyList())
        adapter = StudentAdapter(emptyList())
        binding?.run {
            rvQuestion.adapter = questionAdapter
            rvQuestion.layoutManager = LinearLayoutManager(requireContext())
            rvStudent.adapter = adapter
            rvStudent.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun switchView() {
        binding?.run {
            when(currentView) {
                "Students" -> {
                    rvQuestion.visibility = View.GONE
                    rvStudent.visibility = View.VISIBLE
                }
                else -> {
                    rvQuestion.visibility = View.VISIBLE
                    rvStudent.visibility = View.GONE
                }
            }
        }
    }
}