package com.stefware.myapplication.ui.meetings

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Meeting
import com.stefware.myapplication.data.repository.MeetingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Necesitas crear este modelo primero
data class Meeting(
    val id: Int = 0,
    val title: String = "",
    val date: String = "",
    val time: String = "",
    val link: String = "",
    val host: Int = 0,
    val members: List<Int> = emptyList(),
    val accessCode: String = "",
    val recording: Recording? = null
)

data class Recording(
    val recordingLink: String = "",
    val duration: String = "",
    val publicAccess: Boolean = false
)

@HiltViewModel
class MeetingViewModel @Inject constructor(
    private val meetingRepository: MeetingRepository
) : ViewModel() {

    private val _meetings = MutableStateFlow<List<Meeting>>(emptyList())
    val meetings: StateFlow<List<Meeting>> = _meetings

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val showMeetingDialog = mutableStateOf(false)
    val currentMeeting = mutableStateOf<Meeting?>(null)

    init {
        loadMeetings()
    }

    private fun loadMeetings() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = meetingRepository.getMeetings()
                _meetings.value = result
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun openAddMeetingDialog() {
        currentMeeting.value = Meeting()
        showMeetingDialog.value = true
    }

    fun openEditMeetingDialog(meeting: Meeting) {
        currentMeeting.value = meeting
        showMeetingDialog.value = true
    }

    fun closeMeetingDialog() {
        showMeetingDialog.value = false
    }

    fun saveMeeting(meeting: Meeting) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                if (meeting.id == 0) {
                    meetingRepository.createMeeting(meeting)
                } else {
                    meetingRepository.updateMeeting(meeting.id, meeting)
                }
                loadMeetings()
                closeMeetingDialog()
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMeeting(id: Int) {
        viewModelScope.launch {
            try {
                meetingRepository.deleteMeeting(id)
                _meetings.value = _meetings.value.filter { it.id != id }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}