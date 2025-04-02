package com.hansheung.mob21firebase.ui.user.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (
    private val authService: AuthService,

): BaseViewModel() {
    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    fun login(email: String, pass:String){
        viewModelScope.launch (Dispatchers.IO) {
            errorHandler {
                //throw CustomException("Login fail")
                val res = authService.login(email,pass)
                if(res){
                    _success.emit(Unit)
                }

            }
        }
    }

    fun loginWithGoogle(context: Context){
        viewModelScope.launch {
            errorHandler { authService.login(context) }?.let{
                if (it) _success.emit(Unit)
            }
        }
    }


}