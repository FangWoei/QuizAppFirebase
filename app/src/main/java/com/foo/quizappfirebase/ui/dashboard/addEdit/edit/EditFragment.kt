package com.foo.quizappfirebase.ui.dashboard.addEdit.edit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.ui.dashboard.addEdit.base.BaseAddEditFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EditFragment : BaseAddEditFragment() {
    override val viewModel: EditViewModel by viewModels()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.etChangeText?.text = "Edit Quiz"
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        lifecycleScope.launch {
            viewModel.quiz.collect { quiz ->
                if (quiz != null) {
                    binding?.run {
                        tvTitle.setText(quiz.title)
                        tvDesc.setText(quiz.desc)
                        tvQuizId.setText(quiz.quizIdForSearch)
                        tvTimeLimit.setText(quiz.timeLimit.toString())
                    }
                }
            }
        }
    }

}