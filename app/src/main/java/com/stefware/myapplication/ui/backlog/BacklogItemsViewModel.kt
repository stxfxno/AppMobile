package com.stefware.myapplication.ui.backlog

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Task
import com.stefware.myapplication.data.model.UserStory
import com.stefware.myapplication.data.repository.UserStoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Modelo para un Epic
data class Epic(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val storyIds: List<Int> = emptyList()
)

@HiltViewModel
class BacklogItemsViewModel @Inject constructor(
    private val userStoryRepository: UserStoryRepository
) : ViewModel() {

    private val _userStories = MutableStateFlow<List<UserStory>>(emptyList())
    val userStories: StateFlow<List<UserStory>> = _userStories

    private val _epics = MutableStateFlow<List<Epic>>(emptyList())
    val epics: StateFlow<List<Epic>> = _epics

    val showUserStoryDialog = mutableStateOf(false)
    val showTaskDialog = mutableStateOf(false)
    val showEpicDialog = mutableStateOf(false)

    val currentUserStory = mutableStateOf(UserStory())
    val currentTask = mutableStateOf(Task())
    val currentEpic = mutableStateOf(Epic())

    val currentUserStoryId = mutableStateOf(0)

    init {
        loadUserStories()
        // Por ahora, simularemos los epics con datos de ejemplo
        loadEpics()
    }

    private fun loadUserStories() {
        viewModelScope.launch {
            try {
                val stories = userStoryRepository.getUserStories()
                _userStories.value = stories
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    private fun loadEpics() {
        // Simulamos algunos epics de ejemplo
        _epics.value = listOf(
            Epic(1, "Autenticación de usuario", "Implementar el sistema de autenticación"),
            Epic(2, "Gestión de sprints", "Funcionalidades para gestionar los sprints")
        )
    }

    fun openAddUserStoryDialog() {
        currentUserStory.value = UserStory()
        showUserStoryDialog.value = true
    }

    fun openEditUserStoryDialog(userStory: UserStory) {
        currentUserStory.value = userStory
        showUserStoryDialog.value = true
    }

    fun closeUserStoryDialog() {
        showUserStoryDialog.value = false
    }

    fun saveUserStory(userStory: UserStory) {
        viewModelScope.launch {
            try {
                if (userStory.id == 0) {
                    userStoryRepository.createUserStory(userStory)
                } else {
                    userStoryRepository.updateUserStory(userStory.id, userStory)
                }
                loadUserStories()
                closeUserStoryDialog()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteUserStory(id: Int) {
        viewModelScope.launch {
            try {
                userStoryRepository.deleteUserStory(id)
                loadUserStories()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun openAddTaskDialog(userStoryId: Int) {
        currentUserStoryId.value = userStoryId
        currentTask.value = Task()
        showTaskDialog.value = true
    }

    fun closeTaskDialog() {
        showTaskDialog.value = false
    }

    fun saveTask(userStoryId: Int, task: Task) {
        viewModelScope.launch {
            try {
                // Buscamos la user story a la que agregaremos la tarea
                val userStory = _userStories.value.find { it.id == userStoryId } ?: return@launch

                // Si es una tarea existente, la reemplazamos; si es nueva, la agregamos
                val updatedTasks = if (task.taskId == 0) {
                    // Nueva tarea
                    val newTask = task.copy(taskId = (userStory.tasks.maxOfOrNull { it.taskId } ?: 0) + 1)
                    userStory.tasks + newTask
                } else {
                    // Tarea existente
                    userStory.tasks.map { if (it.taskId == task.taskId) task else it }
                }

                // Actualizamos la user story con las tareas actualizadas
                val updatedUserStory = userStory.copy(tasks = updatedTasks)
                userStoryRepository.updateUserStory(userStoryId, updatedUserStory)

                loadUserStories()
                closeTaskDialog()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun openAddEpicDialog() {
        currentEpic.value = Epic()
        showEpicDialog.value = true
    }

    fun openEditEpicDialog(epic: Epic) {
        currentEpic.value = epic
        showEpicDialog.value = true
    }

    fun closeEpicDialog() {
        showEpicDialog.value = false
    }

    fun saveEpic(epic: Epic) {
        // Simulamos el guardado de un epic
        viewModelScope.launch {
            try {
                val currentEpics = _epics.value.toMutableList()

                if (epic.id == 0) {
                    // Es un nuevo epic
                    val newId = (currentEpics.maxOfOrNull { it.id } ?: 0) + 1
                    currentEpics.add(epic.copy(id = newId))
                } else {
                    // Es un epic existente
                    val index = currentEpics.indexOfFirst { it.id == epic.id }
                    if (index >= 0) {
                        currentEpics[index] = epic
                    }
                }

                _epics.value = currentEpics
                closeEpicDialog()
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    fun deleteEpic(id: Int) {
        // Simulamos la eliminación de un epic
        _epics.value = _epics.value.filter { it.id != id }
    }
}