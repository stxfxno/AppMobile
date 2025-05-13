// app/src/main/java/com/stefware/myapplication/ui/meetings/MeetingDialog.kt
package com.stefware.myapplication.ui.meetings

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stefware.myapplication.data.model.Meeting

@Composable
fun MeetingDialog(
    meeting: Meeting?,
    onDismiss: () -> Unit,
    onSave: (Meeting) -> Unit
) {
    val initialMeeting = meeting ?: Meeting()
    var title by remember { mutableStateOf(initialMeeting.title) }
    var date by remember { mutableStateOf(initialMeeting.date) }
    var time by remember { mutableStateOf(initialMeeting.time) }
    var link by remember { mutableStateOf(initialMeeting.link) }
    var hostId by remember { mutableStateOf(initialMeeting.host.toString()) }
    var accessCode by remember { mutableStateOf(initialMeeting.accessCode) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (initialMeeting.id == 0) "Add New Meeting" else "Edit Meeting") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = time,
                    onValueChange = { time = it },
                    label = { Text("Time (HH:MM)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = link,
                    onValueChange = { link = it },
                    label = { Text("Meeting Link") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = hostId,
                    onValueChange = {
                        if (it.isEmpty() || it.toIntOrNull() != null) {
                            hostId = it
                        }
                    },
                    label = { Text("Host ID") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = accessCode,
                    onValueChange = { accessCode = it },
                    label = { Text("Access Code") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                val updatedMeeting = initialMeeting.copy(
                    title = title,
                    date = date,
                    time = time,
                    link = link,
                    host = hostId.toIntOrNull() ?: 0,
                    accessCode = accessCode
                )
                onSave(updatedMeeting)
            }) {
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