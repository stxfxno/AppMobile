// app/src/main/java/com/stefware/myapplication/ui/issues/IssueDetailScreen.kt
package com.stefware.myapplication.ui.issues

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stefware.myapplication.data.model.History
import com.stefware.myapplication.data.model.Issue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IssueDetailScreen(
    issueId: Int,
    viewModel: IssueDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val issue by viewModel.issue.collectAsState()

    LaunchedEffect(issueId) {
        viewModel.loadIssue(issueId)
    }

    if (issue == null) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Issue Detail") },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            issue?.let { currentIssue ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = currentIssue.title,
                                style = MaterialTheme.typography.headlineSmall
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text("Description: ${currentIssue.description}")
                            Text("Status: ${currentIssue.status}")
                            Text("Priority: ${currentIssue.priority}")
                            Text("Assigned To: ${currentIssue.assignedTo}")
                            Text("Created By: ${currentIssue.madeBy}")
                            Text("Created On: ${currentIssue.createdIn}")
                            if (currentIssue.resolutionDate != null) {
                                Text("Resolution Date: ${currentIssue.resolutionDate}")
                            }
                        }
                    }
                }

                item {
                    Text(
                        text = "History",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                items(currentIssue.history) { historyItem ->
                    HistoryItem(history = historyItem)
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.openAddHistoryDialog() },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Add History Event")
                    }
                }
            }
        }
    }

    // Diálogo para agregar historial
    if (viewModel.showAddHistoryDialog.value) {
        AddHistoryDialog(
            onDismiss = { viewModel.closeAddHistoryDialog() },
            onAdd = { historyEvent ->
                viewModel.addHistoryEvent(historyEvent)
            }
        )
    }
}

@Composable
fun HistoryItem(history: History) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = history.eventName,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = history.createdDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text("By: ${history.madeBy}")
            Text(history.description)
        }
    }
}

@Composable
fun AddHistoryDialog(
    onDismiss: () -> Unit,
    onAdd: (History) -> Unit
) {
    var eventName by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var madeBy by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Add History Event") },
        text = {
            Column {
                TextField(
                    value = eventName,
                    onValueChange = { eventName = it },
                    label = { Text("Event Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = madeBy,
                    onValueChange = { madeBy = it },
                    label = { Text("Made By") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val newHistory = History(
                        id = 0, // El ID será asignado por el servidor
                        createdDate = java.text.SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault()).format(java.util.Date()),
                        madeBy = madeBy,
                        eventName = eventName,
                        description = description
                    )
                    onAdd(newHistory)
                    onDismiss()
                }
            ) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}