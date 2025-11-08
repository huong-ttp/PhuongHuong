package com.example.uthsmarttasksapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.navigation.NavType
import androidx.navigation.compose.*
import com.example.uthsmarttasksapi.ui.screen.*
import androidx.compose.runtime.Composable
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UthSmartTasksApp()
        }
    }
}

@Composable
fun UthSmartTasksApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "list") {
        composable("list") {
            // ✅ Đảm bảo truyền onBackClick
            TaskListScreen(
                onTaskClick = { id ->
                    navController.navigate("detail/$id")
                },
                onBackClick = { navController.popBackStack() }
            )
        }
        composable(
            "detail/{taskId}",
            arguments = listOf(navArgument("taskId") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("taskId") ?: 0
            TaskDetailScreen(taskId = id, onBack = { navController.popBackStack() })
        }
    }
}