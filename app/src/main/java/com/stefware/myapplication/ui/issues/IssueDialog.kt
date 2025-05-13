// app/src/main/java/com/stefware/myapplication/ui/issues/IssueDialog.kt
package com.stefware.myapplication.ui.issues

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.Issue

@Composable
fun IssueDialog(
    issue: Issue?,
    onDismiss: () -> Unit,
    onSave: (Issue) -> Unit
) {
    val currentIssue = remember { mutableStateOf(issue ?: Issue()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (currentIssue.value.id == 0) "Add New Issue" else "Edit Issue"
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                TextField(
                    value = currentIssue.value.title,
                    onValueChange = {
                        currentIssue.value = currentIssue.value.copy(title = it)
                    },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentIssue.value.description,
                    onValueChange = {
                        currentIssue.value = currentIssue.value.copy(description = it)
                    },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentIssue.value.sprintAssociate,
                    onValueChange = {
                        currentIssue.value = currentIssue.value.copy(sprintAssociate = it)
                    },
                    label = { Text("Sprint") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown para seleccionar prioridad
                ExposedDropdownMenuBox(
                    expanded = false,
                    onExpandedChange = { },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = currentIssue.value.priority,
                        onValueChange = {
                            currentIssue.value = currentIssue.value.copy(priority = it)
                        },
                        label = { Text("Priority") },
                        modifier = Modifier.fillMaxWidth().menuAnchor()
                    )

                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { },
                        modifier = Modifier.exposedDropdownSize()
                    ) {
                        listOf("Low", "Medium", "High").forEach { priority ->
                            DropdownMenuItem(
                                text = { Text(priority) },
                                onClick = {
                                    currentIssue.value = currentIssue.value.copy(priority = priority)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentIssue.value.assignedTo,
                    onValueChange = {
                        currentIssue.value = currentIssue.value.copy(assignedTo = it)
                    },
                    label = { Text("Assigned To") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSave(currentIssue.value) }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
        }
    )
}