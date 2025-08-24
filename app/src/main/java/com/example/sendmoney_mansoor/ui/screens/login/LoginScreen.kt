package com.example.sendmoney_mansoor.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sendmoney_mansoor.data.model.LocalizedString
import com.example.sendmoney_mansoor.ui.common.LanguageSwitcher
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val viewModel = hiltViewModel<LoginViewModel>()
    val languageManager = viewModel.languageManager
    val username by viewModel.username.collectAsState()
    val password by viewModel.password.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = languageManager.getLocalizedString(
                                LocalizedString(
                                    "Sign in",
                                    "تسجيل الدخول"
                                )
                            ),
                            color = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF686F89),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFedf1f8))
                .padding(innerPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        onClick = { focusManager.clearFocus() },
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() })
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    LanguageSwitcher(languageManager = languageManager)
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = languageManager.getLocalizedString(
                            LocalizedString(
                                "SEND MONEY APP",
                                "تطبيق إرسال الأموال"
                            )
                        ),
                        style = MaterialTheme.typography.headlineLarge,
                        fontSize = 22.sp,
                        modifier = Modifier.clickable {
                            viewModel.updateUsername("testuser")
                            viewModel.updatePassword("password123")
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = languageManager.getLocalizedString(
                            LocalizedString(
                                "Welcome to Send Money App!",
                                "مرحبا بك في تطبيق إرسال الأموال!"
                            )
                        ),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    TextField(
                        value = username,
                        onValueChange = { viewModel.updateUsername(it) },
                        placeholder = {
                            Text(
                                languageManager.getLocalizedString(
                                    LocalizedString(
                                        "Email*",
                                        "البريد الإلكتروني*"
                                    )
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = password,
                        onValueChange = { viewModel.updatePassword(it) },
                        placeholder = {
                            Text(
                                languageManager.getLocalizedString(
                                    LocalizedString(
                                        "Password*",
                                        "كلمة المرور*"
                                    )
                                )
                            )
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f)
                    )

                    Spacer(modifier = Modifier.height(76.dp))

                    Button(
                        onClick = {
                            coroutineScope.launch {
                                viewModel.login(onSuccess = onLoginSuccess)
                                if (viewModel.errorMessage.value.isNotEmpty()) {
                                    Toast.makeText(
                                        context,
                                        viewModel.errorMessage.value,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(40.dp),
                        shape = RoundedCornerShape(25.dp),
                        enabled = username.isNotEmpty() && password.isNotEmpty(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (username.isNotEmpty() && password.isNotEmpty())
                                Color(0xFF686F89)
                            else
                                Color.Gray
                        )
                    ) {
                        Text(
                            languageManager.getLocalizedString(
                                LocalizedString(
                                    "Sign in",
                                    "تسجيل الدخول"
                                )
                            ), fontSize = 16.sp
                        )
                    }
                }
            }

            Text(
                text = languageManager.getLocalizedString(
                    LocalizedString(
                        "By proceeding you also agree to the Terms of Service and Privacy Policy",
                        "بالمتابعة، أنت توافق على شروط الخدمة وسياسة الخصوصية"
                    )
                ),
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(36.dp)
            )
        }
    }
}