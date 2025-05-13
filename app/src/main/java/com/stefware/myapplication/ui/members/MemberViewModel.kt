package com.stefware.myapplication.ui.members

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Member
import com.stefware.myapplication.data.repository.StatisticsRepository  // Reutilizamos este repositorio
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    val showMemberDialog = mutableStateOf(false)
    val currentMember = mutableStateOf<Member?>(null)

    init {
        loadMembers()
    }

    private fun loadMembers() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val result = statisticsRepository.getMembers()
                _members.value = result
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun openAddMemberDialog() {
        currentMember.value = Member()
        showMemberDialog.value = true
    }

    fun openEditMemberDialog(member: Member) {
        currentMember.value = member
        showMemberDialog.value = true
    }

    fun closeMemberDialog() {
        showMemberDialog.value = false
    }

    fun saveMember(member: Member) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                // Aquí iría la lógica para guardar o actualizar un miembro
                // Como no tenemos implementado ese método en el repositorio, simularemos el guardado

                if (member.id == 0) {
                    // Si es un nuevo miembro, asignamos un ID y lo agregamos a la lista
                    val newId = (_members.value.maxOfOrNull { it.id } ?: 0) + 1
                    val newMember = member.copy(id = newId)
                    _members.value = _members.value + newMember
                } else {
                    // Si es una actualización, reemplazamos el miembro en la lista
                    _members.value = _members.value.map {
                        if (it.id == member.id) member else it
                    }
                }

                closeMemberDialog()
            } catch (e: Exception) {
                // Manejar error
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteMember(id: Int) {
        viewModelScope.launch {
            try {
                // Simular eliminación
                _members.value = _members.value.filter { it.id != id }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}