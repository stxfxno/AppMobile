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
        // This method is used but not implemented in the API service
        // In a real implementation, you would add this endpoint to the API service
        return issue
    }

    suspend fun updateIssue(id: Int, issue: Issue): Issue {
        // This method is used but not implemented in the API service
        return issue
    }

    suspend fun deleteIssue(id: Int) {
        // This method is used but not implemented in the API service
    }
}