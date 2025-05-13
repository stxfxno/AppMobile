package com.stefware.myapplication.ui.statics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stefware.myapplication.data.model.Member
import com.stefware.myapplication.data.repository.StatisticsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SprintStatistics(
    val sprint: Int,
    val completedPercentage: Double,
    val inProgressPercentage: Double
)

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val statisticsRepository: StatisticsRepository
) : ViewModel() {

    private val _statistics = MutableStateFlow<List<SprintStatistics>>(emptyList())
    val statistics: StateFlow<List<SprintStatistics>> = _statistics

    private val _members = MutableStateFlow<List<Member>>(emptyList())
    val members: StateFlow<List<Member>> = _members

    init {
        loadStatistics()
        loadMembers()
    }

    private fun loadStatistics() {
        viewModelScope.launch {
            try {
                // Simulamos algunos datos de ejemplo para las estadísticas
                _statistics.value = listOf(
                    SprintStatistics(1, 85.0, 15.0),
                    SprintStatistics(2, 65.0, 35.0),
                    SprintStatistics(3, 30.0, 70.0)
                )
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }

    private fun loadMembers() {
        viewModelScope.launch {
            try {
                val membersList = statisticsRepository.getMembers()
                _members.value = membersList
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}