package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stefware.myapplication.data.model.UserStory
import com.stefware.myapplication.ui.common.UiState

@Composable
fun BacklogScreen(
    viewModel: BacklogViewModel = hiltViewModel(),
    navigateToBacklogItems: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Backlog",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(onClick = navigateToBacklogItems) {
                Text("Items")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Product Backlog Section
        val userStoriesState by viewModel.userStories.collectAsState()
        val sprintBacklogState by viewModel.sprintBacklog.collectAsState()

        when (val state = userStoriesState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val userStories = state.data
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Product Backlog",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (userStories.isEmpty()) {
                            Text(
                                text = "No user stories available in the product backlog.",
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        } else {
                            userStories.forEach { userStory ->
                                UserStoryItem(
                                    userStory = userStory,
                                    onAddToSprint = { viewModel.addToSprintBacklog(it) }
                                )
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Product Backlog",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        Button(onClick = { viewModel.loadUserStories() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }

        // Sprint Backlog Section
        when (val state = sprintBacklogState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                val sprintBacklog = state.data
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Sprint Backlog",
                            style = MaterialTheme.typography.titleLarge
                        )

                        // Sprint form inputs
                        Spacer(modifier = Modifier.height(12.dp))

                        val sprintTitle = remember { mutableStateOf("") }
                        val sprintGoal = remember { mutableStateOf("") }
                        val sprintEndDate = remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = sprintTitle.value,
                            onValueChange = { sprintTitle.value = it },
                            label = { Text("Sprint Name") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = sprintGoal.value,
                            onValueChange = { sprintGoal.value = it },
                            label = { Text("Sprint Goal") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = sprintEndDate.value,
                            onValueChange = { sprintEndDate.value = it },
                            label = { Text("End Date (YYYY-MM-DD)") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        if (sprintBacklog.isEmpty()) {
                            Text(
                                text = "No user stories added to this sprint yet. Add stories from the Product Backlog.",
                                modifier = Modifier.padding(vertical = 16.dp)
                            )
                        } else {
                            sprintBacklog.forEach { userStory ->
                                UserStoryItem(
                                    userStory = userStory,
                                    onRemove = { viewModel.removeFromSprintBacklog(it) }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                viewModel.createSprint(
                                    title = sprintTitle.value,
                                    goal = sprintGoal.value,
                                    endDate = sprintEndDate.value
                                )
                            },
                            modifier = Modifier.align(Alignment.End),
                            enabled = sprintBacklog.isNotEmpty() &&
                                    sprintTitle.value.isNotBlank() &&
                                    sprintGoal.value.isNotBlank() &&
                                    sprintEndDate.value.isNotBlank()
                        ) {
                            Text("Create Sprint")
                        }
                    }
                }
            }
            is UiState.Error -> {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Sprint Backlog",
                            style = MaterialTheme.typography.titleLarge
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Error: ${state.message}",
                            color = MaterialTheme.colorScheme.error,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )

                        Button(onClick = { viewModel.loadSprintBacklog() }) {
                            Text("Retry")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserStoryItem(
    userStory: UserStory,
    onAddToSprint: ((UserStory) -> Unit)? = null,
    onRemove: ((UserStory) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "US${userStory.id}",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = userStory.title,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = "Status: ${userStory.status.name}",
                    style = MaterialTheme.typography.bodySmall
                )
            }

            if (onAddToSprint != null) {
                IconButton(onClick = { onAddToSprint(userStory) }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Add,
                        contentDescription = "Add to Sprint"
                    )
                }
            }

            if (onRemove != null) {
                IconButton(onClick = { onRemove(userStory) }) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                        contentDescription = "Remove"
                    )
                }
            }
        }
    }
}