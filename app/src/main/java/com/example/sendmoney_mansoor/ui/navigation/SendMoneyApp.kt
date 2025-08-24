package com.example.sendmoney_mansoor.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sendmoney_mansoor.ui.screens.login.LoginScreen
import com.example.sendmoney_mansoor.ui.screens.login.LoginViewModel
import com.example.sendmoney_mansoor.ui.screens.requestdetails.RequestDetailsScreen
import com.example.sendmoney_mansoor.ui.screens.requests.RequestsScreen
import com.example.sendmoney_mansoor.ui.screens.sendmoney.SendMoneyScreen
import com.example.sendmoney_mansoor.util.LanguageManager

@Composable
fun SendMoneyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val languageManager: LanguageManager = hiltViewModel<LoginViewModel>().languageManager
    val currentLanguage by languageManager.currentLanguage
    val layoutDirection by remember(currentLanguage) { derivedStateOf { languageManager.getLayoutDirection() } }

    key (currentLanguage) {
        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            NavHost(navController = navController, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("send_money") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }
                composable("send_money") {
                    SendMoneyScreen(
                        onRequestSaved = { navController.navigate("requests") }
                    )
                }
                composable("requests") {
                    RequestsScreen(
                        onViewDetails = { requestId ->
                            navController.navigate("request_details/$requestId")
                        }
                    )
                }
                composable("request_details/{requestId}") { backStackEntry ->
                    val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
                    RequestDetailsScreen(requestId = requestId)
                }
            }
        }
    }
}