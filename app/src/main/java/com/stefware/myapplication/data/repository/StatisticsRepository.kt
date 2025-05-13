// app/src/main/java/com/stefware/myapplication/data/repository/StatisticsRepository.kt
package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.Member
import javax.inject.Inject

class StatisticsRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getMembers(): List<Member> {
        return apiService.getMembers()
    }
}