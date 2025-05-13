// app/src/main/java/com/stefware/myapplication/data/repository/IssueRepository.kt
package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.Issue
import javax.inject.Inject

class IssueRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getIssues(): List<Issue> {
        return apiService.getIssues()
    }

    suspend fun createIssue(issue: Issue): Issue {
        // Implementación pendiente en la API
        return issue // Simulación
    }

    suspend fun updateIssue(id: Int, issue: Issue): Issue {
        // Implementación pendiente en la API
        return issue // Simulación
    }

    suspend fun deleteIssue(id: Int) {
        // Implementación pendiente en la API
    }
}