package com.foo.quizappfirebase.ui.dashboard.addEdit.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.foo.quizappfirebase.R
import com.foo.quizappfirebase.databinding.FragmentAddEditBinding
import com.foo.quizappfirebase.ui.base.BaseFragment
import kotlinx.coroutines.launch

abstract class BaseAddEditFragment:BaseFragment<FragmentAddEditBinding>() {
    abstract override val viewModel: BaseAddEditViewModel
    private var selectedCsvFile: Uri? = null
    override fun getLayoutResource(): Int = R.layout.fragment_add_edit

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
                    tvQuizId.text.toString(),
                    tvTimeLimit.text.toString().toIntOrNull() ?: 0,
                    selectedCsvFile
                )
                findNavController().popBackStack()
            }
        }
    }
    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "text/csv"
        }
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                selectedCsvFile = uri
                binding?.tvSelectedFile?.text = uri.lastPathSegment
            }
        }
    }

    companion object {
        private const val FILE_PICKER_REQUEST_CODE = 123
    }

}