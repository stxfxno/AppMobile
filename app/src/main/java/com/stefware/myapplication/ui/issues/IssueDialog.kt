// app/src/main/java/com/stefware/myapplication/ui/issues/IssueDialog.kt
package com.stefware.myapplication.ui.issues

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.Issue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDialog(
    issue: Issue?,
    onDismiss: () -> Unit,
    onSave: (Issue) -> Unit
) {
    val initialIssue = issue ?: Issue()
    var title by remember { mutableStateOf(initialIssue.title) }
    var description by remember { mutableStateOf(initialIssue.description) }
    var sprintAssociate by remember { mutableStateOf(initialIssue.sprintAssociate) }
    var priority by remember { mutableStateOf(initialIssue.priority) }
    var assignedTo by remember { mutableStateOf(initialIssue.assignedTo) }
    var expanded by remember { mutableStateOf(false) }
    val priorities = listOf("Low", "Medium", "High")

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initialIssue.id == 0) "Add New Issue" else "Edit Issue"
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
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

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = sprintAssociate,
                    onValueChange = { sprintAssociate = it },
                    label = { Text("Sprint") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown para seleccionar prioridad
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextField(
                        value = priority,
                        onValueChange = { priority = it },
                        label = { Text("Priority") },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        readOnly = true
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        priorities.forEach { priorityOption ->
                            DropdownMenuItem(
                                text = { Text(priorityOption) },
                                onClick = {
                                    priority = priorityOption
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = assignedTo,
                    onValueChange = { assignedTo = it },
                    label = { Text("Assigned To") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val updatedIssue = initialIssue.copy(
                        title = title,
                        description = description,
                        sprintAssociate = sprintAssociate,
                        priority = priority,
                        assignedTo = assignedTo,
                        status = initialIssue.status.ifEmpty { "New" },
                        madeBy = initialIssue.madeBy.ifEmpty { "Current User" },
                        createdIn = initialIssue.createdIn.ifEmpty {
                            java.time.LocalDate.now().toString()
                        }
                    )
                    onSave(updatedIssue)
                }
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