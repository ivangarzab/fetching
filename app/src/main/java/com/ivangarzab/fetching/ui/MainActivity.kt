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
import com.ivangarzab.tracker.models.FrameInfo
import com.ivangarzab.tracker.models.LayerInfo
import com.ivangarzab.tracker.models.NodeOptions

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
                className = "TextField",
                options = NodeOptions(
                    frame = FrameInfo(0, 0, 0, 0),
                    layer = LayerInfo(borderWidth = 1f, cornerRadius = 4f),
                    text = emailValue,
                    isHidden = false
                )
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
                className = "TextField",
                options = NodeOptions(
                    frame = FrameInfo(0, 0, 0, 0),
                    layer = LayerInfo(borderWidth = 1f, cornerRadius = 4f),
                    text = passwordValue,
                    isHidden = false
                )
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
                        className = "Button",
                        options = NodeOptions(
                            frame = FrameInfo(0, 0, 0, 0),
                            layer = LayerInfo(cornerRadius = 8f),
                            backgroundColor = "#1976D2",
                            text = "Log In",
                            isHidden = false
                        )
                    ) {
                        Button(
                            onClick = {
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
                        className = "TextButton",
                        options = NodeOptions(
                            frame = FrameInfo(0, 0, 0, 0),
                            text = "Sign Up",
                            isHidden = false
                        )
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