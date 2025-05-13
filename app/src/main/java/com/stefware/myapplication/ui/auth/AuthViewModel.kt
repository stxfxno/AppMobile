
// app/src/main/java/com/stefware/myapplication/ui/auth/.kt
package com.stefware.myapplication.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.SignInRequest
import com.stefware.myapplication.data.model.SignUpRequest
import com.stefware.myapplication.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun signIn(username: String, password: String) {
        viewModelScope.launch {
            try {
                val request = SignInRequest(username, password)
                val response = authRepository.signIn(request)
                // Almacenar token y manejar navegación
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun signUp(username: String, password: String) {
        viewModelScope.launch {
            try {
                val request = SignUpRequest(username, password)
                val response = authRepository.signUp(request)
                // Manejar navegación
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}