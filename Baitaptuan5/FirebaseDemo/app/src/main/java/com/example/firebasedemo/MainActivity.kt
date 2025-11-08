package com.example.firebasedemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.firebasedemo.ui.theme.FirebaseDemoTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cấu hình Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        auth = FirebaseAuth.getInstance()

        // Launcher xử lý khi người dùng chọn tài khoản Google
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d("FirebaseAuth", "Đăng nhập thành công: ${auth.currentUser?.email}")
                    } else {
                        Log.e("FirebaseAuth", "Đăng nhập thất bại", task.exception)
                    }
                }
            } catch (e: Exception) {
                Log.e("GoogleSignIn", "Lỗi: ", e)
            }
        }

        setContent {
            FirebaseDemoTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            val signInIntent = googleSignInClient.signInIntent
                            launcher.launch(signInIntent)
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("Đăng nhập bằng Google")
                    }
                }
            }
        }
    }
}