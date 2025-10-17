@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.uicomponents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.material3.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.uicomponents.ui.theme.UIComponentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UIComponentsTheme {
                val nav = rememberNavController()
                Scaffold(topBar = { TopBar(nav) }) { inner ->
                    NavHost(
                        navController = nav,
                        startDestination = "list",
                        modifier = Modifier.padding(inner)
                    ) {
                        composable("list") { ComponentsList(nav) }
                        composable("text") { ScreenContainer { TextDetailScreen() } }
                        composable("images") { ScreenContainer { ImagesScreen() } }
                        composable("textfield") { ScreenContainer { TextFieldScreen() } }
                        composable("password") { ScreenContainer { PasswordFieldScreen() } }
                        composable("column") { ScreenContainer { ColumnDemoScreen() } }
                        composable("row") { ScreenContainer { RowLayoutScreen() } }
                        composable("slider") { ScreenContainer { SizeSliderScreen() } } //
                    }
                }
            }
        }
    }
}

/* ---------------- TopBar có nút Back khi không ở màn list ---------------- */
@Composable
private fun TopBar(nav: NavHostController) {
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val showBack = route != "list"

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = when (route) {
                    "text" -> "Text Detail"
                    "images" -> "Images"
                    "textfield" -> "TextField"
                    "password" -> "PasswordField"
                    "column" -> "Column"
                    "row" -> "Row Layout"
                    "slider" -> "Size & Slider"
                    else -> "UI Components List"
                },
                color = Color(0xFF2B7AF1),
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = { nav.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
    )
}

/* ---------------- Khung container thống nhất cho màn con ---------------- */
@Composable
private fun ScreenContainer(content: @Composable ColumnScope.() -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) { content() }
    }
}

/* ======================= LIST – FULL-WIDTH CARDS ======================= */

private data class Menu(val title: String, val route: String, val subtitle: String)

@Composable
fun ComponentsList(nav: NavHostController) {
    val display = listOf(
        Menu("Text", "text", "Displays text"),
        Menu("Image", "images", "Displays an image")
    )
    val input = listOf(
        Menu("TextField", "textfield", "Input field for text"),
        Menu("PasswordField", "password", "Input field for passwords"),
        Menu("Size & Slider", "slider", "Điều chỉnh kích thước bằng thanh trượt") // <-- thêm vào menu
    )
    val layout = listOf(
        Menu("Column", "column", "Arranges elements vertically"),
        Menu("Row", "row", "Arranges elements horizontally")
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentPadding = PaddingValues(bottom = 20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item { SectionHeader("Display") }
        items(display) { item -> ComponentCardFullWidth(item) { nav.navigate(item.route) } }

        item { SectionHeader("Input") }
        items(input) { item -> ComponentCardFullWidth(item) { nav.navigate(item.route) } }

        item { SectionHeader("Layout") }
        items(layout) { item -> ComponentCardFullWidth(item) { nav.navigate(item.route) } }

        item {
            ElevatedCard(
                onClick = { /* TODO */ },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFFFD9D9)),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 72.dp)
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Tự tìm hiểu", color = Color(0xFFB00020), fontWeight = FontWeight.SemiBold)
                    Text(
                        "Tìm ra tất cả các thành phần UI Cơ bản",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFFB00020)
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.labelLarge.copy(
            color = Color.Gray,
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 6.dp, bottom = 4.dp)
    )
}

@Composable
private fun ComponentCardFullWidth(item: Menu, onClick: () -> Unit) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFFD7E7FF)),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 72.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(item.title, style = MaterialTheme.typography.titleMedium, color = Color(0xFF1E4FA8))
            Spacer(Modifier.height(2.dp))
            Text(item.subtitle, style = MaterialTheme.typography.bodySmall, color = Color(0xFF48618C))
        }
    }
}

/* ======================= TEXT DETAIL ======================= */

@Composable
fun TextDetailScreen() {
    val brownGold = Color(0xFFC28B00)
    val pangram = buildAnnotatedString {
        append("The ")
        withStyle(SpanStyle(textDecoration = TextDecoration.LineThrough)) { append("quick") }
        append(" ")
        withStyle(SpanStyle(color = brownGold, fontWeight = FontWeight.Bold, fontSize = 28.sp)) {
            append("Brown")
        }
        append("\n")
        append("fox ")
        append("j u m p s ")
        withStyle(SpanStyle(fontWeight = FontWeight.Bold, fontStyle = FontStyle.Italic)) {
            append("over")
        }
        append("\n")
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("the") }
        append(" ")
        withStyle(SpanStyle(fontFamily = FontFamily.Serif, fontStyle = FontStyle.Italic, fontWeight = FontWeight.Medium)) {
            append("lazy")
        }
        append(" dog.")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = pangram, fontSize = 22.sp, lineHeight = 30.sp)
            Divider(thickness = 1.dp, color = Color(0x11000000))
        }
    }
}

/* ======================= IMAGES (ảnh URL + local) ======================= */

@Composable
fun ImagesScreen() {
    val url = "https://tuyensinhhuongnghiep.vn/upload/images/2023/09/08/truong-dai-hoc-giao-thong-van-tai-tphcm.jpg"
    val uri = LocalUriHandler.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = url,
            contentDescription = "UTH logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text(
            text = url,
            style = MaterialTheme.typography.bodySmall,
            color = Color(0xFF1E4FA8),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { uri.openUri(url) },
            textDecoration = TextDecoration.Underline
        )
        Image(
            painter = painterResource(id = R.drawable.campus),
            contentDescription = "In-app",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
        )
        Text("In app", style = MaterialTheme.typography.bodySmall, color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
    }
}

/* ======================= TEXTFIELD ======================= */

@Composable
fun TextFieldScreen() {
    var text by remember { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text("TextField", style = MaterialTheme.typography.titleLarge)
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            label = { Text("Thông tin nhập") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
        Text(
            if (text.isBlank()) "Tự động cập nhật dữ liệu theo textfield" else text,
            color = if (text.isBlank()) Color(0xFFB00020) else MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/* ======================= PASSWORD FIELD ======================= */

@Composable
fun PasswordFieldScreen() {
    var pwd by remember { mutableStateOf("") }
    var show by remember { mutableStateOf(false) }

    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        OutlinedTextField(
            value = pwd,
            onValueChange = { pwd = it },
            singleLine = true,
            label = { Text("Mật khẩu") },
            placeholder = { Text("Nhập mật khẩu") },
            visualTransformation = if (show) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { show = !show }) {
                    Icon(
                        imageVector = if (show) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                        contentDescription = if (show) "Ẩn mật khẩu" else "Hiện mật khẩu"
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        )

        Text(
            if (pwd.isBlank()) "Nhập mật khẩu" else "Đã nhập ${pwd.length} ký tự",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

/* ======================= COLUMN DEMO ======================= */

@Composable
fun ColumnDemoScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        repeat(3) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = if (it == 1) 1f else 0.25f),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
        }
    }
}

/* ======================= ROW LAYOUT ======================= */

@Composable
fun RowLayoutScreen() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        repeat(4) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                repeat(3) { col ->
                    val isMiddle = col == 1
                    Box(
                        Modifier
                            .weight(1f)
                            .aspectRatio(2.2f)
                            .background(
                                color = if (isMiddle)
                                    MaterialTheme.colorScheme.primary
                                else
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                                shape = RoundedCornerShape(16.dp)
                            )
                    )
                }
            }
        }
    }
}

/* ======================= SIZE & SLIDER (màn mới) ======================= */

@Composable
fun SizeSliderScreen() {
    var font by remember { mutableStateOf(20f) }    // 12..40
    var img by remember { mutableStateOf(180f) }    // 100..260
    var radius by remember { mutableStateOf(16f) }  // 8..32

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Font size
        Text("Font size: ${font.toInt()}sp", style = MaterialTheme.typography.titleSmall)
        Slider(
            value = font,
            onValueChange = { font = it },
            valueRange = 12f..40f,
            steps = 28
        )
        Text("The quick brown fox jumps over the lazy dog.", fontSize = font.sp)

        Divider()

        // Image size
        Text("Image size: ${img.toInt()}dp", style = MaterialTheme.typography.titleSmall)
        Slider(
            value = img,
            onValueChange = { img = it },
            valueRange = 100f..260f,
            steps = 16
        )

        // Corner radius
        Text("Corner radius: ${radius.toInt()}dp", style = MaterialTheme.typography.titleSmall)
        Slider(
            value = radius,
            onValueChange = { radius = it },
            valueRange = 8f..32f,
            steps = 12
        )

        Image(
            painter = painterResource(id = R.drawable.campus),
            contentDescription = "preview",
            modifier = Modifier
                .size(img.dp)
                .clip(RoundedCornerShape(radius.dp))
                .align(Alignment.CenterHorizontally)
        )
    }
}
