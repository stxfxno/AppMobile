// Archivo: app/src/main/java/com/stefware/myapplication/data/repository/MeetingRepository.kt
package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.data.model.Meeting
import javax.inject.Inject

class MeetingRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getMeetings(): List<Meeting> {
        return apiService.getMeetings()
    }

    suspend fun createMeeting(meeting: Meeting): Meeting {
        return apiService.createMeeting(meeting)
    }

    suspend fun updateMeeting(id: Int, meeting: Meeting): Meeting {
        return apiService.updateMeeting(id, meeting)
    }

    suspend fun deleteMeeting(id: Int) {
        apiService.deleteMeeting(id)
    }
}