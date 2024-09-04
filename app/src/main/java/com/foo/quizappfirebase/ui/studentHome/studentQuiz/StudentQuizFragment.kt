package com.foo.quizappfirebase.ui.studentHome.studentQuiz

import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.databinding.FragmentStudentQuizBinding
import com.foo.quizappfirebase.ui.adapter.StudentQuestionAdapter
import com.foo.quizappfirebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StudentQuizFragment : BaseFragment<FragmentStudentQuizBinding>() {
    private lateinit var adapter: StudentQuestionAdapter
    override val viewModel: StudentQuizViewModel by viewModels()
    private var timer: CountDownTimer? = null
    private var timeInMillis: Long? = null
    private var totalSeconds: Int? = null
    private val answers = mutableListOf<String>()
    override fun getLayoutResource(): Int = R.layout.fragment_student_quiz

    override fun onBindView(view: View) {
        super.onBindView(view)
        setupQuesAdapter()
        viewModel.getUserRole()
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect {
                if (it != null) {
                    adapter.setQuestion(it.questions)
                    setupQuiz(it)
                    // Only start the timer if timeInMillis is set
                    if (timeInMillis != null) {
                        startTimer()
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.role1.collect {
                adapter.setRole(it)
            }
        }
    }
    private fun startTimer() {
        timer = object: CountDownTimer(timeInMillis!!, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeInMillis = millisUntilFinished
                updateTimer()
            }
            override fun onFinish() {
                endQuiz()
            }
        }
        timer?.start()
    }
    private fun updateTimer() {
        timeInMillis?.let {
            val seconds = (it / 1000L).toInt()
            binding?.tvViewTimer?.text = requireContext().getString(
                R.string.timer_display, seconds.toString()
            )
        }
    }
    private fun endQuiz() {
        timer?.cancel()
        viewModel.getCurrentQuiz()?.let {
            val score = viewModel.getScore(adapter.getAnswerList())
            val timeTaken = viewModel.getSeconds(it.timeLimit) - (timeInMillis!! / 1000L).toInt()
            viewModel.updateQuiz(score, timeTaken)
            lifecycleScope.launch {
                findNavController().navigate(
                    StudentQuizFragmentDirections.actionStudentQuizFragmentToScoreFragment(
                        score,
                        timeTaken
                    )
                )
            }
        }
    }


    private fun setupQuiz(quiz: Quiz) {
        binding?.run {
            tvViewTitle.text = quiz.title
            tvViewDesc.text = quiz.desc
            totalSeconds = viewModel.getSeconds(quiz.timeLimit)
            timeInMillis = totalSeconds!! * 1000L
            binding?.tvViewTimer?.text = totalSeconds.toString()
            quiz.questions.forEach { answers.add(it.correctAnswer) }
            btnEndQuiz.setOnClickListener { endQuiz() }
        }
    }
    private fun setupQuesAdapter() {
        adapter = StudentQuestionAdapter(emptyList())
        binding?.rvQuesList?.adapter = adapter
        binding?.rvQuesList?.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        timer?.cancel()
        super.onDestroyView()
    }
}