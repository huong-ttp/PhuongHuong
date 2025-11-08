package com.example.uthsmarttasksapi.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.sp
import com.example.uthsmarttasksapi.R
import com.example.uthsmarttasksapi.data.model.Task
import com.example.uthsmarttasksapi.data.network.RetrofitInstance
import kotlinx.coroutines.launch

// ✅ Khắc phục lỗi: Thêm @OptIn cho các API Material 3 thử nghiệm
@OptIn(ExperimentalMaterial3Api::class)
@Composable
// Đảm bảo có onBackClick
fun TaskListScreen(onTaskClick: (Int) -> Unit, onBackClick: () -> Unit) {
    val coroutineScope = rememberCoroutineScope()
    var tasks by remember { mutableStateOf<List<Task>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    // Giữ lại errorMessage cho debug nhưng không dùng để hiển thị riêng
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // 🟢 Logic Gọi API (GIỮ NGUYÊN - Đảm bảo gán tasks = emptyList() khi có lỗi)
    LaunchedEffect(Unit) {
        coroutineScope.launch {
            try {
                val response = RetrofitInstance.api.getTasks() // [cite: 5]
                if (response.isSuccessful) {
                    tasks = response.body()?.data ?: emptyList() // [cite: 4]
                    errorMessage = null
                } else {
                    // Lỗi 404/API lỗi -> tasks = emptyList()
                    tasks = emptyList()
                    errorMessage = "Lỗi tải dữ liệu (${response.code()})"
                }
            } catch (e: Exception) {
                // Lỗi kết nối -> tasks = emptyList()
                tasks = emptyList()
                errorMessage = "Không thể kết nối server: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    // --- CẤU TRÚC SCAFFOLD ---
    Scaffold(
        topBar = {
            // Top Bar
            CenterAlignedTopAppBar(
                title = { Text("List", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        when {
            isLoading -> Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            // ✅ Khi tasks.isEmpty() (bao gồm cả trường hợp lỗi API), hiển thị Empty View
            tasks.isEmpty() -> {
                // 💤 MÀN HÌNH "NO TASKS YET" (Ảnh 2)
                // Dùng Surface để đảm bảo màu nền trắng rõ ràng hơn Box thông thường
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    color = Color.White,
                    contentColor = MaterialTheme.colorScheme.onSurface
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFF0F0F0)) // Màu nền Card xám nhạt
                                .padding(horizontal = 48.dp, vertical = 56.dp)
                                .widthIn(max = 300.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.notasksyet),
                                contentDescription = "No Tasks Yet",
                                modifier = Modifier
                                    .size(96.dp)
                                    .padding(bottom = 12.dp)
                            )
                            Text(
                                text = "No Tasks Yet!",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Stay productive—add something to do",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color.Gray.copy(alpha = 0.8f)
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            else -> {
                // ✅ HIỂN THỊ DANH SÁCH TASK
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    // Logo UTH
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "UTH Logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(90.dp)
                            .padding(8.dp)
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(tasks) { task ->
                            val bgColor = when (task.status) {
                                "In Progress" -> Color(0xFFFFCDD2)
                                "Pending" -> Color(0xFFC8E6C9)
                                else -> Color(0xFFBBDEFB)
                            }

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onTaskClick(task.id) },
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = bgColor),
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(Modifier.padding(16.dp)) {
                                    Text(
                                        text = task.title,
                                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                    )
                                    Spacer(Modifier.height(4.dp))
                                    Text(task.description)
                                    Spacer(Modifier.height(8.dp))
                                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                                        Text("Status: ${task.status}", fontWeight = FontWeight.SemiBold)
                                        Text("⏰ ${task.time}", color = Color.Gray)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
