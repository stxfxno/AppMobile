package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.Task
import com.stefware.myapplication.data.model.UserStory
import javax.inject.Inject

class UserStoryRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getUserStories(): List<UserStory> {
        return apiService.getUserStories()
    }

    suspend fun createUserStory(userStory: UserStory): UserStory {
        return apiService.createUserStory(userStory)
    }

    suspend fun updateUserStory(id: Int, userStory: UserStory): UserStory {
        return apiService.updateUserStory(id, userStory)
    }

    suspend fun deleteUserStory(id: Int) {
        apiService.deleteUserStory(id)
    }

    suspend fun addTask(userStoryId: Int, task: Task): UserStory {
        return apiService.addTask(userStoryId, task)
    }
}