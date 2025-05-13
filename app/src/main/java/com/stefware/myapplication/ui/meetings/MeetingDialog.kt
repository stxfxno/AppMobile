package com.stefware.myapplication.ui.meetings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.Meeting
import com.stefware.myapplication.data.model.Recording

@Composable
fun MeetingDialog(
    meeting: Meeting?,
    onDismiss: () -> Unit,
    onSave: (Meeting) -> Unit
) {
    val currentMeeting = remember { mutableStateOf(meeting ?: Meeting()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (currentMeeting.value.id == 0) "Add New Meeting" else "Edit Meeting") },
        text = {
            Column {
                TextField(
                    value = currentMeeting.value.title,
                    onValueChange = { currentMeeting.value = currentMeeting.value.copy(title = it) },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMeeting.value.date,
                    onValueChange = { currentMeeting.value = currentMeeting.value.copy(date = it) },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMeeting.value.time,
                    onValueChange = { currentMeeting.value = currentMeeting.value.copy(time = it) },
                    label = { Text("Time (HH:MM)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMeeting.value.link,
                    onValueChange = { currentMeeting.value = currentMeeting.value.copy(link = it) },
                    label = { Text("Meeting Link") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMeeting.value.host.toString(),
                    onValueChange = {
                        try {
                            val hostId = it.toIntOrNull() ?: 0
                            currentMeeting.value = currentMeeting.value.copy(host = hostId)
                        } catch (e: Exception) {
                            // Ignorar conversion inválida
                        }
                    },
                    label = { Text("Host ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMeeting.value.accessCode,
                    onValueChange = { currentMeeting.value = currentMeeting.value.copy(accessCode = it) },
                    label = { Text("Access Code") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(currentMeeting.value) }) {
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