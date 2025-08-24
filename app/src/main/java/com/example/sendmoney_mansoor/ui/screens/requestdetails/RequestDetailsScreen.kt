package com.example.sendmoney_mansoor.ui.screens.requestdetails


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sendmoney_mansoor.data.model.LocalizedString
import com.google.gson.GsonBuilder

@Composable
fun RequestDetailsScreen(
    requestId: String,
    viewModel: RequestDetailsViewModel = hiltViewModel()
) {
    val request by viewModel.request
    val languageManager = viewModel.languageManager
    LaunchedEffect(requestId) {
        viewModel.loadRequest(requestId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = languageManager.getLocalizedString(LocalizedString("Request Details", "تفاصيل الطلب")),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (request == null) {
            Text(
                text = languageManager.getLocalizedString(LocalizedString("Request not found", "الطلب غير موجود"))
            )
        } else {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonString = gson.toJson(request)
            Text(
                text = jsonString,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth(),
                textAlign = if (languageManager.currentLanguage.value == "ar") TextAlign.End else TextAlign.Start
            )
        }
    }
}