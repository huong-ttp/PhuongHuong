@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.thuchanh1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

private const val MILLION = 1_000_000

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ImReadyApp() }
    }
}

object Routes {
    const val Onboarding = "onboarding"
    const val Menu = "menu"
    const val ColumnDemo = "column_demo"
    const val LazyColumnDemo = "lazycolumn_demo"
}

@Composable
fun ImReadyApp() {
    val nav = rememberNavController()
    MaterialTheme(colorScheme = lightColorScheme()) {
        NavHost(navController = nav, startDestination = Routes.Onboarding) {
            composable(Routes.Onboarding) { OnboardingScreen(onReady = { nav.navigate(Routes.Menu) }) }
            composable(Routes.Menu) { MenuScreen(nav) }
            composable(Routes.ColumnDemo) { ColumnDemo(nav) }
            composable(Routes.LazyColumnDemo) { LazyColumnDemo(nav) }
        }
    }
}

@Composable
fun OnboardingScreen(onReady: () -> Unit) {
    Scaffold(
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = onReady,
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4C8DF6)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) { Text("I'm ready", fontSize = 16.sp, color = Color.White) }
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(80.dp))

            // Ảnh logo
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFEFF3FF)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo",
                    modifier = Modifier.size(110.dp)
                )
            }

            Spacer(Modifier.height(32.dp))
            Text(
                text = "Jetpack Compose",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold, fontSize = 18.sp
                )
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Jetpack Compose is a modern UI toolkit for " +
                        "building native Android applications using a " +
                        "declarative programming approach.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF6B6B6B), lineHeight = 19.sp
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
fun MenuScreen(nav: NavHostController) {
    Scaffold(topBar = { TopAppBar(title = { Text("Choose a layout") }) }) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { nav.navigate(Routes.ColumnDemo) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("Column") }

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = { nav.navigate(Routes.LazyColumnDemo) },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp)
            ) { Text("LazyColumn") }
        }
    }
}

/**
 * Column + verticalScroll với 1,000,000 item
 * Có nút "Back to Home" để quay về màn hình logo.
 */
@Composable
fun ColumnDemo(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Column demo (1,000,000)") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            // Nút quay về màn hình chính
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        nav.navigate(Routes.Onboarding) {
                            popUpTo(Routes.Onboarding) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) { Text("Back to Home") }
            }
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 12.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            repeat(MILLION) { i ->
                Surface(
                    tonalElevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Row $i",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
            Spacer(Modifier.height(80.dp)) // chừa chỗ cho bottomBar
        }
    }
}

/**
 * LazyColumn với 1,000,000 item
 * Có nút "Back to Home" để quay về màn hình logo.
 */
@Composable
fun LazyColumnDemo(nav: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LazyColumn demo (1,000,000)") },
                navigationIcon = {
                    IconButton(onClick = { nav.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Button(
                    onClick = {
                        nav.navigate(Routes.Onboarding) {
                            popUpTo(Routes.Onboarding) { inclusive = false }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp)
                ) { Text("Back to Home") }
            }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(bottom = 80.dp) // chừa chỗ cho bottomBar
        ) {
            items(count = MILLION, key = { it }) { i ->
                Surface(
                    tonalElevation = 1.dp,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Row $i",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }
    }
}
