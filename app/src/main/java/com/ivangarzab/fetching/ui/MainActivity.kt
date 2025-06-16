package com.ivangarzab.fetching.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.ivangarzab.fetching.ui.theme.FetchingTheme
import com.ivangarzab.tracker.composables.TrackComposable
import com.ivangarzab.tracker.managers.ComplianceCollector

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchingTheme {
                LoginScreen()
            }
        }
    }
}

// Example usage - manually instrumented screen
@Composable
fun LoginScreen() {

    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    TrackComposable("LoginScreen") {
        Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

            TrackComposable("HeaderSection") {
                Text(
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineLarge,
                    modifier = Modifier.padding(bottom = 32.dp)
                )
            }

            TrackComposable(
                name = "EmailField",
                data = mapOf("value" to emailValue, "label" to "Email")
            ) {
                TextField(
                    value = emailValue,
                    onValueChange = { emailValue = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TrackComposable(
                name = "PasswordField",
                data = mapOf("value" to passwordValue, "label" to "Password")
            ) {
                TextField(
                    value = passwordValue,
                    onValueChange = { passwordValue = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TrackComposable("ButtonSection") {
                Column {
                    TrackComposable(
                        name = "LoginButton",
                        data = mapOf("text" to "Log In", "enabled" to "true")
                    ) {
                        Button(
                            onClick = {
                                // Print JSON when login is clicked
                                val json = ComplianceCollector.generateJSON()
                                println("HIERARCHY JSON:")
                                println(json)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Log In")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    TrackComposable(
                        name = "SignUpButton",
                        data = mapOf("text" to "Sign Up", "enabled" to "true")
                    ) {
                        TextButton(
                            onClick = { },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Sign Up")
                        }
                    }
                }
            }
        }
    }
}

// Test function to see the JSON output
fun testHierarchyCapture() {
    // This would be called after composing the screen
    val json = ComplianceCollector.generateJSON()
    println("Generated JSON:")
    println(json)
}