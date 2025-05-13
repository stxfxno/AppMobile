package com.stefware.myapplication.ui.backlog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Sprint
import com.stefware.myapplication.data.model.SprintStatus
import com.stefware.myapplication.data.model.UserStory
import com.stefware.myapplication.data.repository.SprintRepository
import com.stefware.myapplication.data.repository.UserStoryRepository
import com.stefware.myapplication.ui.common.UiState
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
    private val userStoryRepository: UserStoryRepository,
    private val sprintRepository: SprintRepository
) : ViewModel() {

    private val _userStories = MutableStateFlow<UiState<List<UserStory>>>(UiState.Loading)
    val userStories: StateFlow<UiState<List<UserStory>>> = _userStories

    private val _sprintBacklog = MutableStateFlow<UiState<List<UserStory>>>(UiState.Loading)
    val sprintBacklog: StateFlow<UiState<List<UserStory>>> = _sprintBacklog

    // Lista temporal para almacenar las user stories seleccionadas para el sprint backlog
    private val sprintBacklogItems = mutableListOf<UserStory>()

    init {
        loadUserStories()
        loadSprintBacklog()
    }

    fun loadUserStories() {
        viewModelScope.launch {
            _userStories.value = UiState.Loading
            try {
                val stories = userStoryRepository.getUserStories()
                _userStories.value = UiState.Success(stories.filter { it.sprintId == null })
            } catch (e: Exception) {
                _userStories.value = UiState.Error(e.message ?: "Failed to load user stories")
            }
        }
    }

    fun loadSprintBacklog() {
        // Inicialmente solo cargamos la lista local
        _sprintBacklog.value = UiState.Success(sprintBacklogItems)
    }

    fun addToSprintBacklog(userStory: UserStory) {
        val currentState = _userStories.value
        if (currentState is UiState.Success) {
            val updatedStories = currentState.data.filter { it.id != userStory.id }
            _userStories.value = UiState.Success(updatedStories)

            sprintBacklogItems.add(userStory)
            _sprintBacklog.value = UiState.Success(sprintBacklogItems.toList())
        }
    }

    fun removeFromSprintBacklog(userStory: UserStory) {
        val currentState = _sprintBacklog.value
        if (currentState is UiState.Success) {
            sprintBacklogItems.removeIf { it.id == userStory.id }
            _sprintBacklog.value = UiState.Success(sprintBacklogItems.toList())

            // Volver a agregar a la lista de product backlog
            val productBacklogState = _userStories.value
            if (productBacklogState is UiState.Success) {
                val updatedStories = productBacklogState.data + userStory
                _userStories.value = UiState.Success(updatedStories)
            }
        }
    }

    fun createSprint(title: String, goal: String, endDate: String) {
        if (sprintBacklogItems.isEmpty()) return

        viewModelScope.launch {
            try {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val sprint = Sprint(
                    id = 0,
                    title = title,
                    goal = goal,
                    status = SprintStatus.STARTED,
                    startDate = dateFormat.format(Date()),
                    endDate = endDate
                )

                // Crear sprint en el servidor
                val createdSprint = sprintRepository.createSprint(sprint)

                // Actualizar user stories para asignarlas al sprint
                sprintBacklogItems.forEach { userStory ->
                    val updatedUserStory = userStory.copy(sprintId = createdSprint.id)
                    userStoryRepository.updateUserStory(userStory.id, updatedUserStory)
                }

                // Limpiar sprint backlog
                sprintBacklogItems.clear()
                _sprintBacklog.value = UiState.Success(emptyList())

                // Recargar user stories
                loadUserStories()

            } catch (e: Exception) {
                _sprintBacklog.value = UiState.Error(e.message ?: "Failed to create sprint")
            }
        }
    }
}