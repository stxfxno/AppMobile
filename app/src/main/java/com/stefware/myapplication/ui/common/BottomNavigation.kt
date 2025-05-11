package com.stefware.myapplication.ui.common


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.stefware.myapplication.R

@Composable
fun BottomNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavItem("backlog", "Backlog", R.drawable.ic_backlog),
        BottomNavItem("issues", "Issues", R.drawable.ic_issues),
        BottomNavItem("members", "Members", R.drawable.ic_members),
        BottomNavItem("meetings", "Meetings", R.drawable.ic_meetings),
        BottomNavItem("statistics", "Statistics", R.drawable.ic_statistics)
    )

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.iconRes), contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}

data class BottomNavItem(
    val route: String,
    val title: String,
    val iconRes: Int
)