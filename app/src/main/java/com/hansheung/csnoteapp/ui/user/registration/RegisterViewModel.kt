package com.hansheung.mob21firebase.ui.user.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hansheung.mob21firebase.core.service.AuthService
import com.hansheung.mob21firebase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authService: AuthService
) : BaseViewModel() {

    private val _success = MutableSharedFlow<Unit>()
    val success = _success.asSharedFlow()

    fun register(email: String, pass: String, confirmPass: String){
        viewModelScope.launch {
            errorHandler {
                //Throw illegalArgumentException
                require(email.length > 5 ){"Invalid email"}
                require(pass == confirmPass){"Password does not match"}
                authService.register(email,pass)
                _success.emit(Unit)
            }
        }
    }


}