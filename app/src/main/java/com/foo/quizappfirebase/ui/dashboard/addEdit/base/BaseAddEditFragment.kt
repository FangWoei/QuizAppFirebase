package com.foo.quizappfirebase.ui.dashboard.addEdit.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import androidx.activity.result.contract.ActivityResultContracts
import com.foo.quizappfirebase.databinding.FragmentAddEditBinding
import com.foo.quizappfirebase.ui.base.BaseFragment

abstract class BaseAddEditFragment : BaseFragment<FragmentAddEditBinding>() {
    abstract override val viewModel: BaseAddEditViewModel
    private var selectedCsvFile: Uri? = null
    override fun getLayoutResource(): Int = R.layout.fragment_add_edit

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            selectedCsvFile = it
            binding?.tvSelectedFile?.text = it.lastPathSegment
            viewModel.getQuestionsFromCSV(it)
        }
    }

    override fun onBindView(view: View) {
        super.onBindView(view)
        binding?.run {
            btnUploadCsv.setOnClickListener {
                openFilePicker()
            }
            btnSubmit.setOnClickListener {
                viewModel.submit(
                    tvTitle.text.toString(),
                    tvDesc.text.toString(),
                    tvTimeLimit.text.toString(),
                    selectedCsvFile
                )
            }
        }
    }

    private fun openFilePicker() {
        getContent.launch("text/csv")
    }
}



