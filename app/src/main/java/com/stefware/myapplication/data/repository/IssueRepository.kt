// Archivo: app/src/main/java/com/stefware/myapplication/ui/issues/IssueRepository.kt
package com.stefware.myapplication.ui.issues

import com.stefware.myapplication.data.model.Issue
import com.stefware.myapplication.data.repository.IssueRepository as DataIssueRepository
import javax.inject.Inject

/**
 * Esta clase actúa como un adaptador entre el ViewModel y el repositorio de datos real.
 * Permite separar la lógica de presentación de la lógica de acceso a datos.
 */
class IssueRepository @Inject constructor(
    private val dataRepository: DataIssueRepository
) {
    suspend fun getIssues(): List<Issue> {
        return dataRepository.getIssues()
    }

    suspend fun createIssue(issue: Issue): Issue {
        return dataRepository.createIssue(issue)
    }

    suspend fun updateIssue(id: Int, issue: Issue): Issue {
        return dataRepository.updateIssue(id, issue)
    }

    suspend fun deleteIssue(id: Int) {
        dataRepository.deleteIssue(id)
    }
}