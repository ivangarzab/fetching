package com.ivangarzab.fetching.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ivangarzab.fetching.ui.theme.FetchingTheme
import com.ivangarzab.tracker.extensions.ComponentType
import com.ivangarzab.tracker.extensions.complianceTrack
import com.ivangarzab.tracker.managers.ComplianceCollector
import com.ivangarzab.webview.data.WebContent
import com.ivangarzab.webview.data.WebViewState
import com.ivangarzab.webview.data.rememberWebViewState
import com.ivangarzab.webview.ui.WebView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FetchingTheme {
//                LoginScreen()
                FormScreen()
            }
        }
    }
}

@Composable
fun LoginScreen() {
    var emailValue by remember { mutableStateOf("") }
    var passwordValue by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .complianceTrack("LoginScreen", ComponentType.CONTENT)
    ) {

        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .complianceTrack("HeaderText", ComponentType.CONTENT, value = "Welcome Back")
        )

        TextField(
            value = emailValue,
            onValueChange = { emailValue = it },
            label = { Text("Email") },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("EmailField", ComponentType.INPUT, value = emailValue)
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = passwordValue,
            onValueChange = { passwordValue = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("PasswordField", ComponentType.INPUT, value = passwordValue, masked = true)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val json = ComplianceCollector.generateJSON()
                println("ENHANCED HIERARCHY JSON:")
                println(json)
            },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("LoginButton", ComponentType.BUTTON, enabled = true)
        ) {
            Text("Log In")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("SignUpButton", ComponentType.BUTTON, enabled = true)
        ) {
            Text("Sign Up")
        }
    }
}

@Composable
fun FormScreen() {
    var nameValue by remember { mutableStateOf("") }
    var isChecked by remember { mutableStateOf(false) }
    val webState = rememberWebViewState("https://github.com/ivangarzab/composable-webview")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .complianceTrack("FormScreen", ComponentType.CONTENT)
    ) {
        Text(
            text = "Form Signature",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(bottom = 32.dp)
                .complianceTrack("HeaderText", ComponentType.CONTENT, value = "Form Signature")
        )

        Spacer(modifier = Modifier.height(8.dp))

        WebView(
            modifier = Modifier
                .fillMaxWidth()
                .size(150.dp)
                .complianceTrack("FormWebView", ComponentType.WEBVIEW, url = webState.lastLoadedUrl),
            state = webState
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = nameValue,
            onValueChange = { nameValue = it },
            label = { Text("Full Name") },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("NameTextField", ComponentType.INPUT, value = nameValue)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("SwitchRow", ComponentType.CONTENT)
        ) {
            Switch(
                modifier = Modifier
                    .weight(1f)
                    .complianceTrack("SwitchField", ComponentType.SWITCH, value = isChecked.toString()),
                checked = isChecked,
                onCheckedChange = { isChecked = it }
            )
            Text(
                modifier = Modifier
                    .weight(4f)
                    .align(Alignment.CenterVertically)
                    .complianceTrack("SwitchText", ComponentType.CONTENT, value = "I consent to the terms and conditions"),
                text = "I consent to the terms and conditions",
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Button(
            onClick = {
                val json = ComplianceCollector.generateJSON()
                println("ENHANCED FORM JSON:")
                println(json)
            },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("SubmitButton", ComponentType.BUTTON, enabled = true)
        ) {
            Text("Submit")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            onClick = { },
            modifier = Modifier
                .fillMaxWidth()
                .complianceTrack("CancelButton", ComponentType.BUTTON, enabled = false)
        ) {
            Text("Cancel")
        }
    }
}

@Preview
@Composable
fun FormScreenPreview() {
    FormScreen()
}
