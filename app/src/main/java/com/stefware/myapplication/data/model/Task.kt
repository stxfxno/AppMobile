// app/src/main/java/com/stefware/myapplication/data/model/Task.kt
package com.stefware.myapplication.data.model

data class Task(
    val taskId: Int = 0,
    val title: String = "",
    val description: String = "",
    val status: TaskStatus = TaskStatus.TO_DO,
    val estimation: Int = 0
)

enum class TaskStatus {
    TO_DO, IN_PROGRESS, DONE
}