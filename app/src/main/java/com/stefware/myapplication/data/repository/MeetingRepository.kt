package com.stefware.myapplication.data.repository

import com.stefware.myapplication.data.api.ManageWiseApiService
import com.stefware.myapplication.ui.meetings.Meeting
import javax.inject.Inject

class MeetingRepository @Inject constructor(
    private val apiService: ManageWiseApiService
) {
    suspend fun getMeetings(): List<Meeting> {
        // Debes agregar este método a tu ManageWiseApiService
        return apiService.getMeetings()
    }

    suspend fun createMeeting(meeting: Meeting): Meeting {
        // Debes agregar este método a tu ManageWiseApiService
        return apiService.createMeeting(meeting)
    }

    suspend fun updateMeeting(id: Int, meeting: Meeting): Meeting {
        // Debes agregar este método a tu ManageWiseApiService
        return apiService.updateMeeting(id, meeting)
    }

    suspend fun deleteMeeting(id: Int) {
        // Debes agregar este método a tu ManageWiseApiService
        apiService.deleteMeeting(id)
    }
}