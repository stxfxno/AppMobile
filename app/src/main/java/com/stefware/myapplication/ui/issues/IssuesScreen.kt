package com.stefware.myapplication.ui.issues

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stefware.myapplication.data.model.Issue

@Composable
fun IssuesScreen(
    viewModel: IssuesViewModel = hiltViewModel(),
    navigateToIssueDetail: (Int) -> Unit
) {
    val issues by viewModel.issues.collectAsState()
    val selectedSprint by viewModel.selectedSprint.collectAsState()
    val selectedPriority by viewModel.selectedPriority.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Issues",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { viewModel.openAddIssueDialog() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Add Issue")
        }

        // Filter controls
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            // Sprint filter dropdown
            // Priority filter dropdown
            // ...
        }

        // Issues grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(issues.size) { index ->
                val issue = issues[index]
                IssueCard(
                    issue = issue,
                    onViewMore = { navigateToIssueDetail(issue.id) },
                    onEdit = { viewModel.openEditIssueDialog(issue) },
                    onDelete = { viewModel.deleteIssue(issue) },
                    onAddHistoryEvent = { viewModel.openAddHistoryEventDialog(issue) }
                )
            }
        }
    }

    // Add Issue Dialog
    if (viewModel.showAddIssueDialog.value) {
        IssueDialog(
            issue = viewModel.currentIssue.value,
            onDismiss = { viewModel.closeAddIssueDialog() },
            onSave = { viewModel.saveIssue(it) }
        )
    }
}

@Composable
fun IssueCard(
    issue: Issue,
    onViewMore: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onAddHistoryEvent: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = issue.title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "Assigned to: ${issue.assignedTo}")
            Text(text = "Created by: ${issue.madeBy}")
            Text(text = "Status: ${issue.status}")
            Text(text = "Priority: ${issue.priority}")
            Text(text = "Sprint: ${issue.sprintAssociate}")

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onViewMore) {
                    Text("View More")
                }

                Button(onClick = onAddHistoryEvent) {
                    Text("Add History")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = onEdit) {
                    Text("Edit")
                }

                Button(
                    onClick = onDelete,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Delete")
                }
            }
        }
    }
}