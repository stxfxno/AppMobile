// app/src/main/java/com/stefware/myapplication/data/repository/AuthRepository.kt
package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.SignInRequest
import com.stefware.myapplication.data.model.SignInResponse
import com.stefware.myapplication.data.model.SignUpRequest
import com.stefware.myapplication.data.model.SignUpResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun signIn(request: SignInRequest): SignInResponse {
        return apiService.signIn(request)
    }

    suspend fun signUp(request: SignUpRequest): SignUpResponse {
        return apiService.signUp(request)
    }
}