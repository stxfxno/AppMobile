package com.stefware.myapplication.ui.members

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stefware.myapplication.data.model.Member

@Composable
fun MemberManagementScreen(
    viewModel: MemberViewModel = hiltViewModel()
) {
    val members by viewModel.members.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Team Members",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            onClick = { viewModel.openAddMemberDialog() },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Add Member")
        }

        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            LazyColumn {
                items(members.size) { index ->
                    val member = members[index]
                    MemberCard(
                        member = member,
                        onEdit = { viewModel.openEditMemberDialog(member) },
                        onDelete = { viewModel.deleteMember(member.id) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    // Diálogo para agregar/editar miembros
    if (viewModel.showMemberDialog.value) {
        MemberDialog(
            member = viewModel.currentMember.value,
            onDismiss = { viewModel.closeMemberDialog() },
            onSave = { viewModel.saveMember(it) }
        )
    }
}

@Composable
fun MemberCard(
    member: Member,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = member.fullName,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text("Email: ${member.email}")
            Text("Role: ${member.role}")
            Text("Address: ${member.streetAddress}")

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
            }
        }
    }
}

@Composable
fun MemberDialog(
    member: Member?,
    onDismiss: () -> Unit,
    onSave: (Member) -> Unit
) {
    val currentMember = remember { mutableStateOf(member ?: Member()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (currentMember.value.id == 0) "Add New Member" else "Edit Member") },
        text = {
            Column {
                TextField(
                    value = currentMember.value.fullName,
                    onValueChange = { currentMember.value = currentMember.value.copy(fullName = it) },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMember.value.email,
                    onValueChange = { currentMember.value = currentMember.value.copy(email = it) },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMember.value.role,
                    onValueChange = { currentMember.value = currentMember.value.copy(role = it) },
                    label = { Text("Role") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = currentMember.value.streetAddress,
                    onValueChange = { currentMember.value = currentMember.value.copy(streetAddress = it) },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(onClick = { onSave(currentMember.value) }) {
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