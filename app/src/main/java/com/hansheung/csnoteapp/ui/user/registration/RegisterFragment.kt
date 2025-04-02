package com.hansheung.mob21firebase.ui.user.registration

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.hansheung.csnoteapp.databinding.FragmentRegisterBinding
import com.hansheung.mob21firebase.ui.base.BaseFragment
import com.hansheung.mob21firebase.ui.user.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment() {
    override val viewModel: RegisterViewModel by viewModels()
    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.run{
            tvLogin.setOnClickListener {
                findNavController().navigate(
                    RegisterFragmentDirections.actionToLoginFragment()
                )
            }
            btnRegister.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                val confirmPass = etPassword2.text.toString()
                viewModel.register(email, password, confirmPass)
                lifecycleScope.launch {
                    viewModel.success.collect{
                        showSuccess(view, "Successfully Registered")
                    }
                }

                //findNavController().popBackStack()
            }
        }
    }

    private fun showSuccess(view: View, msg: String){
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).apply{
            setBackgroundTint(
                ContextCompat.getColor(
                    requireContext(),
                    com.google.android.material.R.color.call_notification_answer_color
                )
            )
        }.show()
    }
}