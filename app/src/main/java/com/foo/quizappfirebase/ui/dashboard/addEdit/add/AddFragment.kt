package com.foo.quizappfirebase.ui.dashboard.addEdit.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.ui.dashboard.addEdit.base.BaseAddEditFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint

class AddFragment : BaseAddEditFragment() {
    override val viewModel: AddViewModel by viewModels()

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.etChangeText?.text = "Add Quiz"
        binding?.uploadCsvPart?.visibility = View.VISIBLE

    }


}