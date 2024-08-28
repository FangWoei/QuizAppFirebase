package com.foo.quizappfirebase.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.foo.quizappfirebase.core.utils.ResourceProvider
import com.foo.quizappfirebase.data.model.Quiz
import com.foo.quizappfirebase.databinding.ItemQuizBinding

class QuizAdapter(
    resourceProvider: ResourceProvider,
    private var quizzes: List<Quiz>
) : RecyclerView.Adapter<QuizAdapter.QuizViewHolder>() {
    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizViewHolder =
        QuizViewHolder(
            ItemQuizBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    override fun onBindViewHolder(holder: QuizViewHolder, position: Int) =
        holder.bind(quizzes[position])

    override fun getItemCount(): Int = quizzes.size

    fun setQuestions(quizzes: List<Quiz>) {
        this.quizzes = quizzes
        notifyDataSetChanged()
    }

    inner class QuizViewHolder(
        private val binding: ItemQuizBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.run {
                tvTitle.text = quiz.title
                cvQuiz.setOnClickListener { listener?.onClickItem(quiz) }
                ivDelete.setOnClickListener { listener?.onDeleteItem(quiz) }
                ivEdit.setOnClickListener {listener?.onClickEditItem(quiz)}
            }
        }
    }

    interface Listener {
        fun onClickItem(quiz: Quiz)
        fun onClickEditItem(quiz: Quiz)
        fun onDeleteItem(quiz: Quiz)
    }
}