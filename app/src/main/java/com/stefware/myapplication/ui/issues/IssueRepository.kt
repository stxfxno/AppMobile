package com.stefware.myapplication.ui.issues

import com.stefware.myapplication.data.model.Issue
import com.stefware.myapplication.data.repository.IssueRepository as DataIssueRepository
import javax.inject.Inject

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