package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.Task
import com.stefware.myapplication.data.model.TaskStatus

@Composable
fun TaskDialog(
    userStoryId: Int,
    task: Task,
    onDismiss: () -> Unit,
    onSave: (Int, Task) -> Unit
) {
    val currentTask = remember { mutableStateOf(task) }
    val statusOptions = TaskStatus.values().map { it.name }
    var selectedStatus by remember { mutableStateOf(task.status.name) }
    var estimationValue by remember { mutableStateOf(task.estimation.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (task.taskId == 0) "Add Task" else "Edit Task")
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = currentTask.value.title,
                    onValueChange = { currentTask.value = currentTask.value.copy(title = it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentTask.value.description,
                    onValueChange = { currentTask.value = currentTask.value.copy(description = it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Status")
                Spacer(modifier = Modifier.height(4.dp))

                statusOptions.forEach { status ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedStatus == status,
                            onClick = { selectedStatus = status }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(status)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = estimationValue,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            estimationValue = it
                        }
                    },
                    label = { Text("Estimation (hours)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Actualizar el status y estimation del task
                    val status = TaskStatus.valueOf(selectedStatus)
                    val estimation = estimationValue.toIntOrNull() ?: 0

                    val updatedTask = currentTask.value.copy(
                        status = status,
                        estimation = estimation
                    )

                    onSave(userStoryId, updatedTask)
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}