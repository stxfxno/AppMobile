package com.stefware.myapplication.ui.meetings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun MeetingManagementScreen(
    viewModel: MeetingViewModel = hiltViewModel(),
    navigateToRecordings: () -> Unit = {}
) {
    val meetings by viewModel.meetings.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Meetings",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { viewModel.openAddMeetingDialog() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Create Meeting")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(meetings.size) { index ->
                    val meeting = meetings[index]
                    MeetingCard(
                        meeting = meeting,
                        onEdit = { viewModel.openEditMeetingDialog(meeting) },
                        onDelete = { viewModel.deleteMeeting(meeting.id) },
                        onViewRecording = navigateToRecordings
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // Diálogos para agregar/editar reuniones
    if (viewModel.showMeetingDialog.value) {
        MeetingDialog(
            meeting = viewModel.currentMeeting.value,
            onDismiss = { viewModel.closeMeetingDialog() },
            onSave = { viewModel.saveMeeting(it) }
        )
    }
}

@Composable
fun MeetingCard(
    meeting: Meeting,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onViewRecording: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = meeting.title,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Date: ${meeting.date}")
            Text("Time: ${meeting.time}")
            Text("Host: ${meeting.host}")
            Text("Access Code: ${meeting.accessCode}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onEdit) {
                    Text("Edit")
                }

                Button(onClick = onDelete) {
                    Text("Delete")
                }

                if (meeting.recording != null) {
                    Button(onClick = onViewRecording) {
                        Text("Recording")
                    }
                }
            }
        }
    }
}

@Composable
fun MeetingDialog(
    meeting: Meeting?,
    onDismiss: () -> Unit,
    onSave: (Meeting) -> Unit
) {
    // Implementa este diálogo para agregar/editar reuniones
    // Similar a IssueDialog o UserStoryDialog
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (meeting?.id == 0) "Add New Meeting" else "Edit Meeting") },
        text = {
            // Formulario para meeting
            Column {
                // Campos de texto para los datos
            }
        },
        confirmButton = {
            Button(onClick = { /* onSave(meeting) */ }) {
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