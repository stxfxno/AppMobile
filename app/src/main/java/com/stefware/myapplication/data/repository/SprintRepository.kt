package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.Sprint
import javax.inject.Inject

class SprintRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getSprints(): List<Sprint> {
        return apiService.getSprints()
    }

    suspend fun createSprint(sprint: Sprint): Sprint {
        return apiService.createSprint(sprint)
    }
}