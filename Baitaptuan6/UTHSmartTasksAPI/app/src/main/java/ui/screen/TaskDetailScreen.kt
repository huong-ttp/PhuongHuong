package com.example.uthsmarttasksapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.uthsmarttasksapi.R
import com.example.uthsmarttasksapi.data.model.Task
import com.example.uthsmarttasksapi.data.network.RetrofitInstance
import kotlinx.coroutines.launch

@Composable
fun TaskDetailScreen(taskId: Int, onBack: () -> Unit) {
    val scope = rememberCoroutineScope()
    var task by remember { mutableStateOf<Task?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(taskId) {
        scope.launch {
            try {
                val response = RetrofitInstance.api.getTaskDetail(taskId)
                if (response.isSuccessful) {
                    task = response.body()?.data
                } else {
                    task = null
                }
            } catch (e: Exception) {
                task = null
            } finally {
                isLoading = false
            }
        }
    }

    when {
        isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) {
            CircularProgressIndicator()
        }

        task == null -> {
            // 💤 Hiển thị màn hình “No Tasks Yet”
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(24.dp)
                ) {
                    // 🖼 Ảnh no tasks
                    Image(
                        painter = painterResource(id = R.drawable.notasksyet),
                        contentDescription = "No Tasks Yet",
                        modifier = Modifier
                            .size(180.dp)
                            .padding(bottom = 16.dp)
                    )

                    Text(
                        text = "No Tasks Yet!",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Stay productive — add something to do",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 🔙 Nút quay lại
                    Button(
                        onClick = onBack,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("← Quay lại")
                    }
                }
            }
        }

        else -> {
            val t = task!!
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Thanh điều hướng
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                    IconButton(onClick = {
                        scope.launch {
                            RetrofitInstance.api.deleteTask(t.id)
                            onBack()
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
                    }
                }

                // Nội dung task
                Text(
                    t.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                )
                Text(t.description)

                // 3 ô Category / Status / Priority
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    InfoBox("Category", t.category, Color(0xFFFFF3E0))
                    InfoBox("Status", t.status, Color(0xFFFFCDD2))
                    InfoBox("Priority", t.priority, Color(0xFFFFECB3))
                }

                Divider(thickness = 1.dp)

                // 📝 Subtasks
                Text(
                    "Subtasks",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
                t.subtasks.forEach { sub ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(checked = false, onCheckedChange = {})
                        val subText = when (sub) {
                            is Map<*, *> -> sub["name"]?.toString() ?: "Unnamed task"
                            else -> sub.toString()
                        }
                        Text(text = subText)
                    }
                }

                // 📎 Attachments
                if (t.attachments.isNotEmpty()) {
                    Text(
                        "Attachments",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                    t.attachments.forEach { attach ->
                        val fileName = when (attach) {
                            is Map<*, *> -> attach["file"]?.toString() ?: "Unknown file"
                            else -> attach.toString()
                        }
                        Text(text = "📎 $fileName", color = Color.Blue)
                    }
                }
            }
        }
    }
}

@Composable
fun InfoBox(label: String, value: String, bgColor: Color) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(bgColor)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        Text(value, style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
    }
}
