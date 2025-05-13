// app/src/main/java/com/stefware/myapplication/ui/issues/IssueDetailViewModel.kt
package com.stefware.myapplication.ui.issues

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.History
import com.stefware.myapplication.data.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssueDetailViewModel @Inject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {

    private val _issue = MutableStateFlow<Issue?>(null)
    val issue: StateFlow<Issue?> = _issue

    val showAddHistoryDialog = mutableStateOf(false)

    fun loadIssue(issueId: Int) {
        viewModelScope.launch {
            try {
                // Esta es una simulación. En una implementación real, deberías obtener el issue por su ID
                val issues = issueRepository.getIssues()
                _issue.value = issues.find { it.id == issueId }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun openAddHistoryDialog() {
        showAddHistoryDialog.value = true
    }

    fun closeAddHistoryDialog() {
        showAddHistoryDialog.value = false
    }

    fun addHistoryEvent(historyEvent: History) {
        // Implementar lógica para agregar un evento de historial al issue actual
        val currentIssue = _issue.value ?: return

        // Simulación: Agregar el evento al historial del issue en memoria
        val updatedHistory = currentIssue.history + historyEvent
        _issue.value = currentIssue.copy(history = updatedHistory)

        // En una implementación real, deberías llamar a un método de tu API para guardar el historial
        closeAddHistoryDialog()
    }
}