// app/src/main/java/com/stefware/myapplication/ui/backlog/BacklogItemsScreen.kt
package com.stefware.myapplication.ui.backlog

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun BacklogItemsScreen(
    viewModel: BacklogItemsViewModel = hiltViewModel(),
    navigateToBacklog: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("User Stories", "Epics")

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Backlog Items",
                style = MaterialTheme.typography.headlineMedium
            )

            Button(onClick = navigateToBacklog) {
                Text("Back to Backlog")
            }
        }

        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> UserStoriesTab(viewModel)
            1 -> EpicsTab(viewModel)
        }
    }
}

@Composable
fun UserStoriesTab(viewModel: BacklogItemsViewModel) {
    val userStories by viewModel.userStories.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "User Stories",
                style = MaterialTheme.typography.titleLarge
            )

            Button(onClick = { viewModel.openAddUserStoryDialog() }) {
                Text("Add")
            }
        }

        if (userStories.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No user stories found. Add one to get started.")
            }
        } else {
            userStories.forEach { userStory ->
                UserStoryCard(
                    userStory = userStory,
                    onEdit = { viewModel.openEditUserStoryDialog(userStory) },
                    onDelete = { viewModel.deleteUserStory(userStory.id) },
                    onAddTask = { viewModel.openAddTaskDialog(userStory.id) }
                )
            }
        }
    }

    // Diálogos para agregar/editar
    if (viewModel.showUserStoryDialog.value) {
        UserStoryDialog(
            userStory = viewModel.currentUserStory.value,
            onDismiss = { viewModel.closeUserStoryDialog() },
            onSave = { viewModel.saveUserStory(it) }
        )
    }

    if (viewModel.showTaskDialog.value) {
        TaskDialog(
            userStoryId = viewModel.currentUserStoryId.value,
            task = viewModel.currentTask.value,
            onDismiss = { viewModel.closeTaskDialog() },
            onSave = { userStoryId, task -> viewModel.saveTask(userStoryId, task) }
        )
    }
}

@Composable
fun EpicsTab(viewModel: BacklogItemsViewModel) {
    val epics by viewModel.epics.collectAsState()

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Epics",
                style = MaterialTheme.typography.titleLarge
            )

            Button(onClick = { viewModel.openAddEpicDialog() }) {
                Text("Add")
            }
        }

        if (epics.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text("No epics found. Add one to get started.")
            }
        } else {
            epics.forEach { epic ->
                EpicCard(
                    epic = epic,
                    onEdit = { viewModel.openEditEpicDialog(epic) },
                    onDelete = { viewModel.deleteEpic(epic.id) }
                )
            }
        }
    }

    // Diálogo para agregar/editar épicas
    if (viewModel.showEpicDialog.value) {
        EpicDialog(
            epic = viewModel.currentEpic.value,
            onDismiss = { viewModel.closeEpicDialog() },
            onSave = { viewModel.saveEpic(it) }
        )
    }
}

// Los componentes UserStoryCard, EpicCard, UserStoryDialog, TaskDialog, y EpicDialog
// deberían implementarse por separado