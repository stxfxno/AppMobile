package com.stefware.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.stefware.myapplication.ui.navigation.NavigationGraph
import com.stefware.myapplication.ui.theme.ManageWiseTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ManageWiseTheme {
                val navController = rememberNavController()
                NavigationGraph(navController = navController)
            }
        }
    }
}