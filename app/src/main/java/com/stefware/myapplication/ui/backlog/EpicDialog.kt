package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EpicDialog(
    epic: Epic,
    onDismiss: () -> Unit,
    onSave: (Epic) -> Unit
) {
    val currentEpic = remember { mutableStateOf(epic) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (epic.id == 0) "Add Epic" else "Edit Epic")
        },
        text = {
            Column(modifier = Modifier.padding(8.dp)) {
                TextField(
                    value = currentEpic.value.title,
                    onValueChange = { currentEpic.value = currentEpic.value.copy(title = it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentEpic.value.description,
                    onValueChange = { currentEpic.value = currentEpic.value.copy(description = it) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(currentEpic.value)
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