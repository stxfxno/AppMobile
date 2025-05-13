// Archivo: app/src/main/java/com/stefware/myapplication/ui/navigation/NavigationGraph.kt
package com.stefware.myapplication.ui.navigation

import com.stefware.myapplication.ui.issues.IssuesScreen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.stefware.myapplication.ui.auth.SignInScreen
import com.stefware.myapplication.ui.auth.SignUpScreen
import com.stefware.myapplication.ui.backlog.BacklogItemsScreen
import com.stefware.myapplication.ui.backlog.BacklogScreen
import com.stefware.myapplication.ui.common.ManageWiseScaffold
import com.stefware.myapplication.ui.issues.IssueDetailScreen
import com.stefware.myapplication.ui.issues.IssuesScreen
import com.stefware.myapplication.ui.meetings.MeetingManagementScreen
import com.stefware.myapplication.ui.members.MemberManagementScreen
import com.stefware.myapplication.ui.statistics.StatisticsScreen
import com.stefware.myapplication.ui.statistics.TimelineScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    ManageWiseScaffold(navController = navController) {
        NavHost(
            navController = navController,
            startDestination = "sign_in"
        ) {
            // Authentication
            composable("sign_in") {
                SignInScreen(
                    navigateToSignUp = { navController.navigate("sign_up") },
                    navigateToHome = { navController.navigate("backlog") {
                        popUpTo("sign_in") { inclusive = true }
                    }}
                )
            }

            composable("sign_up") {
                SignUpScreen(
                    navigateToSignIn = { navController.popBackStack() }
                )
            }

            // Backlog
            composable("backlog") {
                BacklogScreen(
                    navigateToBacklogItems = { navController.navigate("backlog_items") }
                )
            }

            composable("backlog_items") {
                BacklogItemsScreen(
                    navigateToBacklog = { navController.popBackStack() }
                )
            }

            // Issues
            composable("issues") {
                IssuesScreen(
                    navigateToIssueDetail = { issueId ->
                        navController.navigate("issue_detail/$issueId")
                    }
                )
            }

            composable("issue_detail/{issueId}") { backStackEntry ->
                val issueId = backStackEntry.arguments?.getString("issueId")?.toIntOrNull() ?: 0
                IssueDetailScreen(
                    issueId = issueId,
                    navigateBack = { navController.popBackStack() }
                )
            }

            // Members
            composable("members") {
                MemberManagementScreen()
            }

            // Meetings
            composable("meetings") {
                MeetingManagementScreen()
            }

            // Statistics
            composable("statistics") {
                StatisticsScreen(
                    navigateToTimeline = { navController.navigate("timeline") }
                )
            }

            composable("timeline") {
                TimelineScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}