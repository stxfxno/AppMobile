package com.stefware.myapplication.data.model

data class Sprint(
    val id: Int = 0,
    val title: String = "",
    val goal: String = "",
    val status: SprintStatus = SprintStatus.STARTED,
    val startDate: String = "",
    val endDate: String = ""
)

enum class SprintStatus {
    STARTED, FINISHED
}