package com.foo.quizappfirebase.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

abstract class BaseFragment<T: ViewBinding> : Fragment() {
    protected abstract val viewModel: BaseViewModel
    protected var binding: T? = null
    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBindView(view)
        onBindData(view)
    }

    protected open fun onBindView(view: View) {
        binding = DataBindingUtil.bind(view)
    }

    protected open fun onBindData(view: View) {
    lifecycleScope.launch {
        viewModel.error.collect{
            Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }
    }
    }
}