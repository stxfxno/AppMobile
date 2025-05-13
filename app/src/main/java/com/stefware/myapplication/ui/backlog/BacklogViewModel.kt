// app/src/main/java/com/stefware/myapplication/ui/backlog/BacklogViewModel.kt
package com.stefware.myapplication.ui.backlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Sprint
import com.stefware.myapplication.data.model.SprintStatus
import com.stefware.myapplication.data.model.UserStory
import com.stefware.myapplication.data.repository.UserStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class BacklogViewModel @Inject constructor(
    private val userStoryRepository: UserStoryRepository
) : ViewModel() {

    private val _userStories = MutableStateFlow<List<UserStory>>(emptyList())
    val userStories: StateFlow<List<UserStory>> = _userStories

    private val _sprintBacklog = MutableStateFlow<List<UserStory>>(emptyList())
    val sprintBacklog: StateFlow<List<UserStory>> = _sprintBacklog

    init {
        loadUserStories()
    }

    private fun loadUserStories() {
        viewModelScope.launch {
            try {
                val stories = userStoryRepository.getUserStories()
                _userStories.value = stories.filter { it.sprintId == null }
            } catch (e: Exception) {
                // Manejar el error
            }
        }
    }

    fun addToSprintBacklog(userStory: UserStory) {
        _userStories.value = _userStories.value.filter { it.id != userStory.id }
        _sprintBacklog.value = _sprintBacklog.value + userStory
    }

    fun removeFromSprintBacklog(userStory: UserStory) {
        _sprintBacklog.value = _sprintBacklog.value.filter { it.id != userStory.id }
        _userStories.value = _userStories.value + userStory
    }

    fun createSprint() {
        if (_sprintBacklog.value.isEmpty()) return

        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val sprint = Sprint(
            id = 0, // El servidor asignará un ID
            title = "Sprint ${System.currentTimeMillis()}", // Puedes cambiar esto por un título ingresado por el usuario
            goal = "Complete ${_sprintBacklog.value.size} user stories",
            status = SprintStatus.STARTED,
            startDate = dateFormat.format(Date()),
            endDate = dateFormat.format(Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000)) // 2 semanas después
        )

        // Aquí deberías llamar a un método para crear el sprint en el servidor
        // y luego actualizar las historias de usuario para asignarlas al sprint

        // Simulación de creación exitosa
        _sprintBacklog.value = emptyList()
    }
}