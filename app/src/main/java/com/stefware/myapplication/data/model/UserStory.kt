package com.stefware.myapplication.data.model

data class UserStory(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val status: StoryStatus = StoryStatus.TO_DO,
    val epicId: Int = 0,
    val sprintId: Int? = null,
    val effort: Int = 0,
    val tasks: List<Task> = emptyList()
)

enum class StoryStatus {
    TO_DO, IN_PROGRESS, DONE
}
