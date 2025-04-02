package com.hansheung.csnoteapp.ui

import androidx.lifecycle.ViewModel
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authService: AuthService
) : BaseViewModel() {

    fun logout(){
        authService.logout()
    }
}