package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stefware.myapplication.data.model.UserStory

@Composable
fun BacklogScreen(
    viewModel: BacklogViewModel = hiltViewModel(),
    navigateToBacklogItems: () -> Unit
) {
    val userStories by viewModel.userStories.collectAsState()
    val sprintBacklog by viewModel.sprintBacklog.collectAsState()

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

                userStories.forEach { userStory ->
                    UserStoryItem(
                        userStory = userStory,
                        onAddToSprint = { viewModel.addToSprintBacklog(it) }
                    )
                }
            }
        }

        // Sprint Backlog Section
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
                // ...

                Spacer(modifier = Modifier.height(8.dp))

                sprintBacklog.forEach { userStory ->
                    UserStoryItem(
                        userStory = userStory,
                        onRemove = { viewModel.removeFromSprintBacklog(it) }
                    )
                }

                Button(
                    onClick = { viewModel.createSprint() },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Create Sprint")
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
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "US${userStory.id}")
                Text(text = userStory.title)
                Text(text = "Status: ${userStory.status}")
            }

            if (onAddToSprint != null) {
                IconButton(onClick = { onAddToSprint(userStory) }) {
                    Icon(Icons.Default.Add, contentDescription = "Add to Sprint")
                }
            }

            if (onRemove != null) {
                IconButton(onClick = { onRemove(userStory) }) {
                    Icon(Icons.Default.Delete, contentDescription = "Remove")
                }
            }
        }
    }
}