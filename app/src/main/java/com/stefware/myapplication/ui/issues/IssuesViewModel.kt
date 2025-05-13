// app/src/main/java/com/stefware/myapplication/ui/issues/IssuesViewModel.kt
package com.stefware.myapplication.ui.issues

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Issue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IssuesViewModel @Inject constructor(
    private val issueRepository: IssueRepository
) : ViewModel() {

    private val _issues = MutableStateFlow<List<Issue>>(emptyList())
    val issues: StateFlow<List<Issue>> = _issues

    private val _selectedSprint = MutableStateFlow<String?>(null)
    val selectedSprint: StateFlow<String?> = _selectedSprint

    private val _selectedPriority = MutableStateFlow<String?>(null)
    val selectedPriority: StateFlow<String?> = _selectedPriority

    val showAddIssueDialog = mutableStateOf(false)
    val currentIssue = mutableStateOf<Issue?>(null)

    init {
        loadIssues()
    }

    private fun loadIssues() {
        viewModelScope.launch {
            try {
                val issuesList = issueRepository.getIssues()
                _issues.value = issuesList
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun filterIssues() {
        viewModelScope.launch {
            try {
                val allIssues = issueRepository.getIssues()
                _issues.value = allIssues.filter { issue ->
                    (selectedSprint.value == null || issue.sprintAssociate == selectedSprint.value) &&
                            (selectedPriority.value == null || issue.priority == selectedPriority.value)
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun openAddIssueDialog() {
        currentIssue.value = Issue()
        showAddIssueDialog.value = true
    }

    fun closeAddIssueDialog() {
        showAddIssueDialog.value = false
    }

    fun openEditIssueDialog(issue: Issue) {
        currentIssue.value = issue
        showAddIssueDialog.value = true
    }

    fun saveIssue(issue: Issue) {
        viewModelScope.launch {
            try {
                if (issue.id == 0) {
                    issueRepository.createIssue(issue)
                } else {
                    issueRepository.updateIssue(issue.id, issue)
                }
                loadIssues()
                closeAddIssueDialog()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteIssue(issue: Issue) {
        viewModelScope.launch {
            try {
                issueRepository.deleteIssue(issue.id)
                _issues.value = _issues.value.filter { it.id != issue.id }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun openAddHistoryEventDialog(issue: Issue) {
        // Implementar lógica para abrir diálogo de historial
    }
}