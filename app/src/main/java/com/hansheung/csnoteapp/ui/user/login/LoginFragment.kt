package com.hansheung.mob21firebase.ui.user.login

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.hansheung.csnoteapp.databinding.FragmentLoginBinding
import com.hansheung.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment() {
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun setupUiComponents(view: View) {
        super.setupUiComponents(view)
        binding.tvRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }

        binding.btnLogin.setOnClickListener {
            viewModel.login(
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString()
            )
        }

        binding.btnGoogleLogin.setOnClickListener {
            viewModel.loginWithGoogle(requireContext())
        }
    }

    override fun setupViewModelObserver() {
        super.setupViewModelObserver()
        lifecycleScope.launch {
            viewModel.success.collect{
                findNavController().navigate(
                    LoginFragmentDirections.actionToHomeFragment()
                )
            }
        }
    }
}