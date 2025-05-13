// app/src/main/java/com/stefware/myapplication/ui/backlog/UserStoryDialog.kt
package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.StoryStatus
import com.stefware.myapplication.data.model.UserStory

@Composable
fun UserStoryDialog(
    userStory: UserStory,
    onDismiss: () -> Unit,
    onSave: (UserStory) -> Unit
) {
    var title by remember { mutableStateOf(userStory.title) }
    var description by remember { mutableStateOf(userStory.description) }
    var selectedStatus by remember { mutableStateOf(userStory.status.name) }
    var effortValue by remember { mutableStateOf(userStory.effort.toString()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (userStory.id == 0) "Add User Story" else "Edit User Story")
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Status")
                Spacer(modifier = Modifier.height(4.dp))

                StoryStatus.values().forEach { status ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        RadioButton(
                            selected = selectedStatus == status.name,
                            onClick = { selectedStatus = status.name }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(status.name)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = effortValue,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            effortValue = it
                        }
                    },
                    label = { Text("Effort Points") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Actualizar el status y effort del user story
                    val status = StoryStatus.valueOf(selectedStatus)
                    val effort = effortValue.toIntOrNull() ?: 0

                    val updatedUserStory = userStory.copy(
                        title = title,
                        description = description,
                        status = status,
                        effort = effort
                    )

                    onSave(updatedUserStory)
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