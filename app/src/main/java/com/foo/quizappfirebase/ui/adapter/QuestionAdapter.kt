package com.foo.quizappfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Question
import com.foo.quizappfirebase.databinding.ItemQuestionBinding
import javax.inject.Inject

class QuestionAdapter(
    private var questions: List<Question>
) : RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
    var listener: QuizAdapter.Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder =
        QuestionViewHolder(
            ItemQuestionBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questions[position])
    }

    override fun getItemCount(): Int = questions.size

    fun setQuestion(question: List<Question>) {
        this.questions = question
        notifyDataSetChanged()
    }


    inner class QuestionViewHolder(
        private val binding: ItemQuestionBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(question: Question) {
            binding.tvQuestion.text = question.questionText

            val options = listOf(binding.RadioA, binding.RadioB, binding.RadioC, binding.RadioD)
            options.forEachIndexed { index, radioButton ->
                radioButton.text = question.options.getOrNull(index) ?: ""
                radioButton.isChecked = question.options.getOrNull(index) == question.correctAnswer
            }
        }
    }
}