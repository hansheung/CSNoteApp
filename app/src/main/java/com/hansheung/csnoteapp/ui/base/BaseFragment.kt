package com.hansheung.mob21firebase.ui.base


import android.os.Bundle
import androidx.fragment.app.Fragment

import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch


abstract class BaseFragment: Fragment() {

    protected abstract val viewModel:BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onFragmentResult()
        setupUiComponents(view)
        setupViewModelObserver()
    }

    protected open fun onFragmentResult(){}

    //
    protected open fun setupViewModelObserver(){
        lifecycleScope.launch {
            viewModel.error.collect{
                showError(requireView(), it)
            }
        }
    }

    //Anything has to do with UI
    protected open fun setupUiComponents(view: View){

    }

    private fun showError(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).apply{
            setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    com.google.android.material.R.color.design_default_color_error
                )
            )
        }.show()
    }
}